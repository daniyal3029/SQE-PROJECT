"""
Purpose: Sample Python Code for Pynguin Test Generation
This file contains simple Python functions for demonstrating AI-assisted testing with Pynguin.

Functions:
1. is_prime(n): Check if a number is prime
2. factorial(n): Calculate factorial of a number
3. is_palindrome(s): Check if a string is a palindrome
"""

def is_prime(n):
    """
    Check if a number is prime.
    
    Args:
        n (int): Number to check
        
    Returns:
        bool: True if prime, False otherwise
    """
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    
    for i in range(3, int(n ** 0.5) + 1, 2):
        if n % i == 0:
            return False
    
    return True


def factorial(n):
    """
    Calculate factorial of a number.
    
    Args:
        n (int): Non-negative integer
        
    Returns:
        int: Factorial of n
        
    Raises:
        ValueError: If n is negative
    """
    if n < 0:
        raise ValueError("Factorial is not defined for negative numbers")
    
    if n == 0 or n == 1:
        return 1
    
    result = 1
    for i in range(2, n + 1):
        result *= i
    
    return result


def is_palindrome(s):
    """
    Check if a string is a palindrome (reads same forwards and backwards).
    
    Args:
        s (str): String to check
        
    Returns:
        bool: True if palindrome, False otherwise
    """
    if not isinstance(s, str):
        return False
    
    # Remove spaces and convert to lowercase for comparison
    cleaned = s.replace(" ", "").lower()
    
    return cleaned == cleaned[::-1]


# Example usage
if __name__ == "__main__":
    # Test is_prime
    print("Prime number tests:")
    print(f"is_prime(7): {is_prime(7)}")      # True
    print(f"is_prime(10): {is_prime(10)}")    # False
    print(f"is_prime(2): {is_prime(2)}")      # True
    
    # Test factorial
    print("\nFactorial tests:")
    print(f"factorial(5): {factorial(5)}")    # 120
    print(f"factorial(0): {factorial(0)}")    # 1
    print(f"factorial(3): {factorial(3)}")    # 6
    
    # Test is_palindrome
    print("\nPalindrome tests:")
    print(f"is_palindrome('racecar'): {is_palindrome('racecar')}")      # True
    print(f"is_palindrome('hello'): {is_palindrome('hello')}")          # False
    print(f"is_palindrome('A man a plan a canal Panama'): {is_palindrome('A man a plan a canal Panama')}")  # True
