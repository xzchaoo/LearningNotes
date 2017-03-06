import os

def walk(root, deep=0):
    if deep > 3:
        return
    with os.scandir(root) as it:
        for entry in it:
            if entry.name.startswith('.') or entry.name.startswith('$') or ' ' in  entry.name:
                continue
            if entry.is_dir():
                print(entry.path)
                walk(entry.path, deep + 1)
            elif entry.is_file():
                print(entry.path)

walk('c:/opt')
