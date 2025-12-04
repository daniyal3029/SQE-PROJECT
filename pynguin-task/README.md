# Pynguin Task - README

## Purpose
This directory contains the additional task for AI-Assisted Testing using Pynguin.

## Files
- `my_code.py`: Python code with 3 functions (is_prime, factorial, is_palindrome)
- `reflection.md`: Reflection on Pynguin-generated tests
- `tests/`: Directory for Pynguin-generated tests (created after running Pynguin)

## Instructions

### 1. Install Pynguin
```bash
pip install pynguin
```

### 2. Generate Test Cases
```bash
cd e:\F233029_SQE_Project\pynguin-task
pynguin --project-path . --module-name my_code --output-path tests
```

### 3. Run Generated Tests
```bash
pip install pytest
pytest tests/ -v
```

### 4. Review and Update Reflection
After running Pynguin and reviewing the generated tests, update `reflection.md` with your observations.

## Expected Deliverables
1. ✅ `my_code.py` - Python code file
2. ⏳ `tests/` folder - Generated tests (after running Pynguin)
3. ✅ `reflection.md` - Reflection document (to be updated after running Pynguin)

## Notes
- Pynguin requires Python 3.8 or higher
- Generated tests will be in pytest format
- You may need to install additional dependencies based on Pynguin's requirements
