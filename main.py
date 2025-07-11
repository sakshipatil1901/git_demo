import math
import os
import random
import re
import sys

def countPromotionalPeriods(orders):
    n = len(orders)
    count = 0
    
    for i in range(n - 2):
        max_middle = orders[i + 1]
        
        for j in range(i + 2, n):
            if min(orders[i], orders[j]) > max_middle:
                count += 1
            
            max_middle = max(max_middle, orders[j])
    
    return count

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')
    
    orders_count = int(input().strip())
    orders = []
    
    for _ in range(orders_count):
        orders_item = int(input().strip())
        orders.append(orders_item)
    
    result = countPromotionalPeriods(orders)
    
    fptr.write(str(result) + '\n')
    fptr.close()