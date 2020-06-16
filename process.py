from analyzer import analyzer
from assembler import assembler
from cleaner import cleaner
from compile import compiler
from io_library import source_reader
from precompile import precompile
import sys

reg = {'AC': 0, 'DR' : 0, 'AR': 0, 'PC' : 100, 'IR' : 0, 'TR' :0, 'INPR':0, 'OUTR' :0}
ACTION = []



#, output_file: str, mode: str
def process(input_file: str, output_file: str, mode : str):
    source = source_reader(input_file)
    source = cleaner(source)
    analyze(source)
    symbols_address = precompile(source)
    obj_dict = assembler(source, symbols_address)
    print(obj_dict)
    print('\n Program compiled successfully!\n')
    compiler(obj_dict, output_file, mode)
    



def analyze(source: str):
    declaration = set()
    usage = set()
    error_counter = 0
    line_number = 1
    lines = source.split('\n')

    for line in lines:
        declaration, usage, err_c = analyzer(line, declaration, usage, line_number)
        error_counter += err_c
        line_number += 1

    if error_counter != 0:
        announce(error_counter)
    else:
        if declaration != usage:
            announce(1)


def announce(error_counter: int):
    print("Compiler found {} errors\nCompile FAILED\n".format(error_counter))
    exit(2)



process(sys.argv[1], sys.argv[2], 't')

print(sys.argv)