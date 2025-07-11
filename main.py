import math
import os
import random
import re
import sys

def countPromotionalPeriods(orders):
    """
    Optimized solution to count promotional periods.
    
    Algorithm:
    - For each starting position i, iterate through ending positions j
    - Maintain running maximum of middle elements to avoid recalculation
    - Check if min(orders[i], orders[j]) > max_middle for each period
    
    Time complexity: O(n^2) - much better than naive O(n^3) approach
    Space complexity: O(1) - only using constant extra space
    """
    n = len(orders)
    count = 0
    
    # For each possible starting position i (can go from 0 to n-3)
    for i in range(n - 2):
        # Initialize max_middle with the first middle element
        max_middle = orders[i + 1]
        
        # For each possible ending position j (minimum period length is 3)
        for j in range(i + 2, n):
            # Check if current period [i, j] is a promotional period
            # Condition: min(orders[i], orders[j]) > max(orders[i+1] to orders[j-1])
            if min(orders[i], orders[j]) > max_middle:
                count += 1
            
            # Update max_middle for next iteration
            # When j increases by 1, orders[j] becomes part of middle elements
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