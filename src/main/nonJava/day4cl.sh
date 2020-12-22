#!/bin/bash
sed 's/^$/blankline/' $1 \
| tr '\n' ' ' \
| sed 's/blankline/\n/g' \
| egrep "\bbyr:(19[2-9][0-9])|(200[0-2])\b" \
| egrep "\biyr:(201[0-9])|(2020)\b" \
| egrep "\beyr:(202[0-9])|(2030)\b" \
| egrep "\bhgt:((1[5-8][0-9]cm)|(19[0-3]cm)|(59in)|(7[0-6]in)|(6[0-9]in))\b" \
| egrep "\bhcl:#[0-9a-f]{6}\b" \
| egrep "\becl:(amb|blu|brn|gry|grn|hzl|oth)\b" \
| egrep "\bpid:[0-9]{9}\b" \
| wc -l

