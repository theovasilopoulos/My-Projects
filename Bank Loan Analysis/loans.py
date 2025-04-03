import json  
import zipfile
import csv
from io import TextIOWrapper

class Applicant:
    def __init__(self, age, race):
        self.age = age
        self.race = set()
        for r in race:
            if r in race_lookup:
                self.race.add(race_lookup[r])
        self.race = set(sorted(self.race))
        
    def __repr__(self):
        return f"Applicant({repr(self.age)}, {repr(list(self.race))})"
    
    def lower_age(self):
        if '-' in self.age:
            return int(self.age.split('-')[0])
        elif '<' in self.age:
            return int(self.age.split('<')[-1])
        elif '>' in self.age:
            return int(self.age.split('>')[-1])

    def __lt__(self, other):
        return self.lower_age() < other.lower_age()
            
race_lookup = {
    "1": "American Indian or Alaska Native",
    "2": "Asian",
    "3": "Black or African American",
    "4": "Native Hawaiian or Other Pacific Islander",
    "5": "White",
    "21": "Asian Indian",
    "22": "Chinese",
    "23": "Filipino",
    "24": "Japanese",
    "25": "Korean",
    "26": "Vietnamese",
    "27": "Other Asian",
    "41": "Native Hawaiian",
    "42": "Guamanian or Chamorro",
    "43": "Samoan",
    "44": "Other Pacific Islander"
}

class Loan:
    def __init__(self, values):
        self.loan_amount = self.float_extract(values["loan_amount"]) # write the float_extract method as specified below
        self.property_value = self.float_extract(values["property_value"])
        self.interest_rate = self.float_extract(values["interest_rate"])
        self.applicants = []
        
        race_list = []
        for i in values:
            if 'applicant_race-' in i and values[i] != '':
                race_list.append(values[i])
        main_app = Applicant(values['applicant_age'], race_list)
        self.applicants.append(main_app)
        
        co_app_age = values['co-applicant_age']
        if co_app_age != '9999':
            co_race_list = []
            for i in values:
                if 'co-applicant_race-' in i and values[i] != '':
                    co_race_list.append(values[i])
            co_app = Applicant(co_app_age, co_race_list)
            self.applicants.append(co_app)
        
    def float_extract(self, string):
        if string == "NA" or string == "Exempt":
            return -1
        else:
            return float(string)
        
    def __str__(self):
        return f"<Loan: {self.interest_rate}% on ${self.loan_amount} with {len(self.applicants)} applicant(s)>"
    
    def __repr__(self):
        return f"<Loan: {self.interest_rate}% on ${self.loan_amount} with {len(self.applicants)} applicant(s)>"

    def yearly_amounts(self, yearly_payment):
        # TODO: assert interest and amount are positive
        amt = self.loan_amount

        while amt > 0 and self.interest_rate > 0: 
            # TODO: yield amt
            yield amt
            # TODO: add interest rate multiplied by amt to amt
            amt += self.interest_rate/100*amt
            # TODO: subtract yearly payment from amt
            amt -= yearly_payment
            

with open('banks.json', 'r') as file:
    banks = json.load(file)


class Bank:
    
    def __init__(self, name):
        
        for bank in banks:
            if bank['name'] == name:
                self.lei = bank['lei']
                break
        
        with zipfile.ZipFile('wi.zip', 'r') as zip_file:
            with zip_file.open('wi.csv') as csvfile:
                text_wrapper = TextIOWrapper(csvfile, encoding='utf-8')
                reader = csv.DictReader(text_wrapper)
                self.loan_list = []        
                for row in reader:
                    if row['lei'] == self.lei:
                        loan = Loan(row)
                        self.loan_list.append(loan)

    def __getitem__(self, lookup):
        return self.loan_list[lookup]

    def __len__(self):
        return len(self.loan_list)