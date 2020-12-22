#!/bin/bash
sed 's/^$/blankline/' $1 \
| tr '\n' ' ' \
| sed 's/blankline/\n/g' \
| egrep "byr:(19[2-9][0-9])|(200[0-2])" \
| egrep "iyr:(201[0-9])|(2020)" \
| egrep "eyr:(202[0-9])|(2030)" \
| egrep "hgt:((1[5-8][0-9]cm)|(19[0-3]cm)|(59in)|(7[0-6]in)|(6[0-9]in))\b" \
| egrep "hcl:#[0-9a-f]{6}" \
| egrep "ecl:(amb|blu|brn|gry|grn|hzl|oth)" \
| egrep "pid:[0-9]{9}\b" \
| wc -l

