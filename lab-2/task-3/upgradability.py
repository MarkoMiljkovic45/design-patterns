def mymax(iterable, key=lambda x: x):
    max_x = max_key = None

    for x in iterable:
        if max_key is None or key(x) > max_key:
            max_x = x
            max_key = key(x)

    return max_x


maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
maxchar = mymax("Suncana strana ulice")
maxstring = mymax(["Gle", "malu", "vocku", "poslije", "kise", "Puna", "je", "kapi", "pa", "ih", "njise"])

D = {'burek': 8, 'buhtla': 5}
maxprice = mymax(D, D.get)

maxperson = mymax([("Ana", "Anić"), ("Bruno", "Brunić"), ("Cvita", "Cvitić")])

print(f"maxint   : {maxint}")
print(f"maxchar  : {maxchar}")
print(f"maxstring: {maxstring}")
print(f"maxprice : {maxprice}")
print(f"maxperson: {maxperson}")