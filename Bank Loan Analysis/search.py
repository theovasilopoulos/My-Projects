class Node():
    def __init__(self, key):
        self.key = key
        self.values = []
        self.left = None
        self.right = None
        
    def __len__(self):
        size = len(self.values)
        if self.left != None:
            size += len(self.left)
        if self.right != None:
            size += len(self.right)
        return size
    
    def lookup(self, key):
        if key == self.key:
            return self.values
        elif key < self.key and self.left is not None:
            return self.left.lookup(key)
        elif key > self.key and self.right is not None:
            return self.right.lookup(key)
        else:
            return []
        
class BST():
    def __init__(self):
        self.root = None
    
    def __getitem__(self, key):
        return self.root.lookup(key)

    def add(self, key, val):
        if self.root == None:
            self.root = Node(key)

        curr = self.root
        while True:
            if key < curr.key:
                # go left
                if curr.left == None:
                    curr.left = Node(key)
                curr = curr.left
            elif key > curr.key:
                # go right
                if curr.right == None:
                    curr.right = Node(key)
                curr = curr.right
            else:
                # found it!
                assert curr.key == key
                break

        curr.values.append(val)
    def __dump(self, node):
        if node == None:
            return
        
        print(node.key, ":", node.values)  # 2
        self.__dump(node.right)            # 1
        self.__dump(node.left)             # 3

    def dump(self):
        self.__dump(self.root)
    
    def height(self):
        return self.height_helper(self.root)

    def height_helper(self, node):
        if node is None:
            return -1
        left_height = self.height_helper(node.left)
        right_height = self.height_helper(node.right)
        return 1 + max(left_height, right_height)