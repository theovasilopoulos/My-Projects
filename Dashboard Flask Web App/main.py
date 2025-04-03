import pandas as pd
import json
from flask import Flask, request, jsonify, Response, send_file
import time
import re
import matplotlib.pyplot as plt
import io
import matplotlib
matplotlib.use('Agg')

# Data Source: https://www.kaggle.com/datasets/ak0212/average-daily-screen-time-for-children?resource=download

app = Flask(__name__)
df = pd.read_csv("main.csv")

# tracking homepage visits
homepage_visit_count = 0
# tracking donation clicks
donation_clicks = {"A": 0, "B": 0}
# best version after 10 visits
best_version = None

@app.route('/')
def home():
    global homepage_visit_count, best_version

    # if version is already determined, use it
    if best_version is not None:
        version = best_version
    else:
        # Alternate between version A and B during the first 10 visits
        if homepage_visit_count < 10:
            version = "A" if homepage_visit_count % 2 == 0 else "B"
        else:
            # after 10 visits determine best version based on donation clicks
            if donation_clicks["A"] >= donation_clicks["B"]:
                best_version = "A"
            else:
                best_version = "B"
            version = best_version

    homepage_visit_count += 1

    # change HTML content based on the version
    with open("index.html") as f:
        html = f.read()
    
    # change the link depending on the version (Blue for A, Red for B)
    if version == "A":
        html = html.replace("{{DONATE_LINK}}", '<a href="donate.html?from=A" style="color:blue;">Donate</a>')
    else:
        html = html.replace("{{DONATE_LINK}}", '<a href="donate.html?from=B" style="color:red;">Donate</a>')

    # Add link to browse.html
    html = html.replace("{{BROWSE_LINK}}", '<a href="browse.html" style="color:green;">Browse Data</a>')

    return html



##################################################################
################################################# browse.html page

@app.route('/browse.html')
def browse_html_handler():
    # converting DataFrame to an HTML table
    html_table = df.to_html(index=False, float_format="%.20g")

    # header above the table
    header = "<h1>Browse Data</h1>"

    # combining header and table
    full_html = header + html_table

    return full_html


##################################################################
################################################# browse.json page

# Dictionary for storing last request times for every IP
last_visit_times = {}

@app.route('/browse.json')
def browse_json_handler():
    client_ip = request.remote_addr  # get IP address
    current_time = time.time()

    # checking if the IP has requested this resource within the last 60 seconds
    if client_ip in last_visit_times and (current_time - last_visit_times[client_ip]) < 60:
        retry_after = 60 - (current_time - last_visit_times[client_ip])
        return Response(
            "<b>Too many requests</b>",
            status=429,
            headers={"Retry-After": str(int(retry_after))}
        )

    last_visit_times[client_ip] = current_time

    json_data = df.to_dict(orient="records")
    return jsonify(json_data)

####################################################################
################################################# visitors.json page

@app.route('/visitors.json')
def visitors_handler():
    return list(last_visit_times.keys())

##################################################################
################################################# donate.html page

@app.route('/donate.html')
def donate_html_handler():
    global donation_clicks, best_version

    # tracking donation clicks
    version = request.args.get("from")
    if version in ["A", "B"]:
        donation_clicks[version] += 1

    # best version after 10 visits
    if homepage_visit_count >= 10 and best_version is None:
        best_version = "A" if donation_clicks["A"] >= donation_clicks["B"] else "B"

    with open("donate.html") as f:
        return f.read()

##################################################################
############################################################ email

num_subscribed = 0

@app.route('/email', methods=["POST"])
def email():
    global num_subscribed
    email = str(request.data, "utf-8")
    if len(re.findall(r"^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{3}$", email)) > 0: # 1
        with open("emails.txt", "a") as f: # open file in append mode
            f.write(email + "\n") # 2
        num_subscribed += 1  # Increment the subscriber count after adding the email
        return jsonify(f"thanks, your subscriber number is {num_subscribed}!")
    return jsonify("Wrong email. Try again.") # 3

##################################################################

@app.route('/dashboard1.svg')
def dashboard1():
    bins = int(request.args.get('bins', 20))  # Default to 20 bins if not provided

    fig, ax = plt.subplots()
    ax.hist(df["Average Screen Time (hours)"], bins=bins, color='skyblue', edgecolor='black')
    ax.set_xlabel('Average Screen Time (hours)')
    ax.set_ylabel('Frequency')
    ax.set_title(f'Screen Time Distribution (Bins={bins})')

    file_path = f"dashboard1-query.svg"
    fig.savefig(file_path, format='svg')
    plt.close(fig)  # Close the plot to free memory

    return send_file(file_path, mimetype='image/svg+xml')


@app.route('/dashboard2.svg')
def dashboard2():
    fig, ax = plt.subplots()
    df.boxplot(column='Average Screen Time (hours)', by='Day Type', ax=ax, patch_artist=True)
    ax.set_ylabel('Average Screen Time (hours)')
    ax.set_title('Screen Time Distribution by Day Type')
    plt.suptitle('')

    file_path = "dashboard2-query.svg"
    fig.savefig(file_path, format='svg')
    plt.close(fig)

    return send_file(file_path, mimetype='image/svg+xml')

@app.route('/dashboard3.svg')
def dashboard3():
    fig, ax = plt.subplots()
    
    ax.hist(df['Age'], bins=10, color='lightgreen', edgecolor='black')
    ax.set_xlabel('Age')
    ax.set_ylabel('Frequency')
    ax.set_title('Age Distribution')

    file_path = "dashboard3-query.svg"
    fig.savefig(file_path, format='svg')
    plt.close(fig)  # Close the plot to free memory

    return send_file(file_path, mimetype='image/svg+xml')

@app.route('/dashboard4.svg')
def dashboard4():
    gender_avg_screen_time = df.groupby('Gender')['Average Screen Time (hours)'].mean()

    fig, ax = plt.subplots()
    
    gender_avg_screen_time.plot(kind='bar', color='orange', edgecolor='black', ax=ax)
    ax.set_xlabel('Gender')
    ax.set_ylabel('Average Screen Time (hours)')
    ax.set_title('Average Screen Time by Gender')

    file_path = "dashboard4-query.svg"
    fig.savefig(file_path, format='svg')
    plt.close(fig)  # Close the plot to free memory

    return send_file(file_path, mimetype='image/svg+xml')

@app.route('/dashboard5.svg')
def dashboard5():
    screen_time_type_avg = df.groupby('Screen Time Type')['Average Screen Time (hours)'].mean()

    fig, ax = plt.subplots()
    
    screen_time_type_avg.plot(kind='bar', color='lightcoral', edgecolor='black', ax=ax)
    ax.set_xlabel('Screen Time Type')
    ax.set_ylabel('Average Screen Time (hours)')
    ax.set_title('Average Screen Time by Screen Time Type')

    file_path = "dashboard5-query.svg"
    fig.savefig(file_path, format='svg')
    plt.close(fig)  # Close the plot to free memory

    return send_file(file_path, mimetype='image/svg+xml')


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, threaded=False)
