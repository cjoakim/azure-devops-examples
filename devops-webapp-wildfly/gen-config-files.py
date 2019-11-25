"""
Description:
  Generate files with dynamic content within an Azure DevOps Pipeline
  with Python, and secrets passed from KeyVault to the Pipeline.
  For example, Wildfly XML config files.
  See files 'gen-config-files.sh' and 'azure-pipelines.yml' in this repo.
  Chris Joakim, Microsoft, 2019/11/25
Usage:
  In your Azure DevOps 'azure-pipelines.yml' file pass params like this:
  - script: ./gen-config-files.sh sample_xml $(AZURE-ACR-USER-NAME) $(AZURE-ACR-USER-PASS)
Options:
  -h --help     Show this screen.
  --version     Show version.
"""

import json
import sys
import time
import os

import jinja2

from docopt import docopt

VERSION = 'v20191125'


def print_options(msg):
    print(msg)
    arguments = docopt(__doc__, version=VERSION)
    print(arguments)

def generate_sample_xml():
    jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(os.path.dirname (__file__)), autoescape=True)
    template = jinja_env.get_template("templates/sample.xml")
    values = dict()
    values['name'] = sys.argv[2]
    values['pass'] = sys.argv[3]
    values['namelen'] = len(values['name'])
    values['passlen'] = len(values['pass'])
    xml = template.render(values)
    print(xml)  # write this to a file so that it can be embedded in the WAR file for Wildfly.


if __name__ == "__main__":

    # print(sys.argv)

    if len(sys.argv) > 1:
        func = sys.argv[1].lower()

        if func == 'sample_xml':
            generate_sample_xml()

        elif func == 'sample_xml':
            generate_sample_xml()

        else:
            print_options('Error: invalid function: {}'.format(func))
    else:
        print_options('Error: no function argument provided.')


