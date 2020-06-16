from __config__ import MRI
from __config__ import NON_MRI
from __config__ import PREUDOMSTRUCTION

instructions = []
def assembler(source: str, symbols_address: dict):
    lines = source.split('\n')
    # Location Counter
    LC = 0
    obj_dict = dict()

    for line in lines:
        strings = line.split()
        switch = len(strings)
        word = strings[0]
        if switch == 1:
            if word == PREUDOMSTRUCTION[3]: #agar end bood
                instructions.append(word)
                break
            else:
                instructions.append(word) # pas non mri bodeh 
                obj_dict[LC] = hex(NON_MRI[word])
        elif switch == 2: # 2 kalamei bashe
            if word == PREUDOMSTRUCTION[0]: # agar ORG bood
                instructions.append(word)
                LC = int(strings[1]) - 1 # mirim hamonja ke eshare kardeh
            else:
                if word in MRI:
                    instructions.append(word)
                    obj_dict[LC] = _assemble(strings, symbols_address, 0, 0)
                    print(strings, ' ', symbols_address,' ', 0, ' ', 0)
                elif word in PREUDOMSTRUCTION:
                    instructions.append(word)
                    obj_dict[LC] = num_converter(word, strings[1])
        elif switch == 3:
            state = True
            if word[-1] == ',':
                a = 1
                b = 0
                if strings[1] in PREUDOMSTRUCTION[1:3]: # pas ye esm boodeh serfan
                    print("HEXXXX")
                    state = False
            else:
                a = 0
                b = 1
            if state: # agar maslan loop bod dastor badi bayad be ye jaye be khosos eshare kone
                instructions.append(word)
                obj_dict[LC] = _assemble(strings, symbols_address, a, b)
            else:
                instructions.append(word)
                obj_dict[LC] = num_converter(strings[1], strings[2])
        elif switch == 4:
            instructions.append(word)
            obj_dict[LC] = _assemble(strings, symbols_address, 1, 1)
        LC += 1
    return obj_dict


def _assemble(strings: list, symbols_address: dict, index1: int, index2: int):
    three_bit_part = symbols_address[strings[index1 + 1]]
    one_bit_part = MRI[strings[index1]][index2]
    if (len(str(hex(int(three_bit_part)))[2:])== 1):
        three_bit_part = '00' + str(hex(int(three_bit_part)))[2:]
    elif (len(str(hex(int(three_bit_part)))[2:]) == 2):
        three_bit_part = '0' + str(hex(int(three_bit_part)))[2:]
    else:
        three_bit_part =  str(hex(int(three_bit_part)))[2:]

    assembled = one_bit_part + three_bit_part
    print(hex(int(assembled, 16)))
    return hex(int(assembled, 16))


def num_converter(word: str, number: str) -> str:
    """ Converts string number to it's appreciate hexadecimal integer

    :param word: HEX or DEC for determining with conversion method to use"
    :param number: string number to be converted to integer
    :return  hexadecimal string representation of input number"""
    if word == PREUDOMSTRUCTION[1]:  # word == HEX
        print("Hello")
        print(hex(int(number, 16)))
        return hex(int(number, 16))
    else:                            # word == DEC
        return hex(int(number))
