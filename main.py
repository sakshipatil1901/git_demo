import math
import os
import random
import re
import sys

def countPromotionalPeriods(orders):
    n = len(orders)
    count = 0
    
    # Clean O(n²) solution with minimal overhead
    for i in range(n - 2):
        order_i = orders[i]
        max_middle = orders[i + 1]
        
        for j in range(i + 2, n):
            order_j = orders[j]
            
            # Inline min calculation
            min_ends = order_i if order_i <= order_j else order_j
            
            # Check condition
            if min_ends > max_middle:
                count += 1
            
            # Update max_middle
            if order_j > max_middle:
                max_middle = order_j
    
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