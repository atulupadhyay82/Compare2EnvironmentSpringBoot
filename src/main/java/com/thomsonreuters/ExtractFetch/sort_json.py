import json
import sys

def sort_json(content):
    if isinstance(content, list):
        new_list = []
        for i in xrange(len(content)):
            new_list.append(sort_json(content[i]))
        return sorted(new_list)

    elif isinstance(content, dict):
        new_dict = {}
        for key in sorted(content):
            new_dict[key] = sort_json(content[key])
        return new_dict

    else:
        return content


with open(sys.argv[1]) as f_input:
    sorted_content = sort_json(json.load(f_input))
    with open(sys.argv[2], 'w') as f_output:
        json.dump(sorted_content, f_output, sort_keys=True, indent=4)
