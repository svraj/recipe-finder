# recipe-finder

Given a list of items in the fridge (presented as a csv list), and a collection of recipes (a collection of JSON formatted recipes), produces a recommendation for what to cook tonight.

Program is written to take two inputs; fridge csv list, and the json recipe data through a web form. 

Input Parameters:

fridge csv

Format: item, amount, unit, use-by
where;
Item (string) = the name of the ingredient – e.g. egg)||
Amount (int) = the amount||
Unit (enum) = the unit of measure, 
values of (for individual items; eggs, bananas etc) grams
ml (milliliters)
slices
Use-By (date) = the use by date of the ingredient (dd/mm/yy)


recipes json

Array of recipes with format specified as below
name : String
ingredients[]
item : String
amount : int
unit : enum

Notes:
An ingredient that is past its use-by date will not be used for cooking.
If more than one recipe is found, then preference will be given to the recipe with the closest use-by item
If no recipe is found, the program will display “Order Takeout”
Program is all-inclusive with a run script 

To Build:
run mvn:clean install from project directory

To Launch:
Click or Run 'launch-recipe-finder.bat' file (for Windows)
./launch-recipe-finder.sh (for Linux)

