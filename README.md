# CS 245 (Fall 2023) - Assignment 3 - IRoadTrip

a) Design choices and OO decomposition of the problem:
The design of the `IRoadTrip` class follows an Object-Oriented (OO) approach to decompose the problem. Here are the design choices and decomposition of the problem:

1. Class Structure:
   - The `IRoadTrip` class is the main class that contains the implementation of the road trip functionality.
   - It has instance variables `mCountryList` and `mAdjustMap` for storing country information and adjacency matrix, respectively.
   - The class also contains helper classes `Node` for representing nodes in the graph.

2. Constructor:
   - The `IRoadTrip` class has a constructor that takes an array of strings `args` as input.
   - The constructor initializes the country list and adjacency map by reading data from files specified in the `args` array.

3. Private Helper Methods:
   - The class contains several private helper methods to assist in various tasks such as data preprocessing and validation.
   - These methods include `validName`, `getCountryName`, and others.
   - These methods are responsible for cleaning up country names, retrieving country names from a map, and performing other necessary operations.

4. Public Methods:
   - The class provides two public methods: `getDistance` and `findPath`.
   - The `getDistance` method takes two country names as input and returns the distance between them based on the adjacency map.
   - The `findPath` method takes two country names as input and finds the shortest path between them using Dijkstra's algorithm.
   - Both methods make use of the adjacency map and other helper methods to perform their respective tasks.

5. User Input Handling:
   - The class includes a method `acceptUserInput` that prompts the user to enter country names and finds the shortest path between them.
   - This method uses a `Scanner` object to read user input and repeatedly calls the `findPath` method to display the shortest path.

b) Data structure choices:
The `IRoadTrip` class uses the following data structures:

1. ArrayList:
   - The `mCountryList` variable is an ArrayList that stores the list of countries.
   - It is used to validate country names and check for their existence.

2. HashMap:
   - The `mAdjustMap` variable is a HashMap that represents the adjacency map.
   - It stores country connections and distances between them.
   - The keys of the outer map represent starting countries, and the values are inner maps.
   - The keys of the inner maps represent destination countries, and the values represent the distances between them.
   - This data structure allows efficient retrieval of distances between countries.

3. PriorityQueue:
   - The `minHeap` variable is a PriorityQueue that is used in the Dijkstra's algorithm implementation.
   - It stores nodes (countries) based on their distances, with the node having the smallest distance at the front.
   - This allows efficient retrieval of the node with the minimum distance during the algorithm execution.

4. Maps:
   - Several `Map` objects are used to store various mappings required for data processing and algorithm execution.
   - Examples include `nameMap` for mapping country IDs to names, `dist` for storing distances during the algorithm, and others.
   - Maps provide efficient key-value lookups, which are useful for retrieving data during the execution of the program.

c) Anything else you believe might be useful:
The provided source code implements a road trip functionality using graph traversal algorithms. It reads data from input files and constructs an adjacency map to represent country connections and distances. The Dijkstra's algorithm is then used to find the shortest path between two countries.

To enhance the functionality and usability of the program, you could consider the following:

1. Error Handling:
   - The current implementation does minimal error handling. You can add more robust error handling and exception handling to handle various scenarios, such as file reading errors, invalid input, or incorrect file formats.

2. Input Validation:
   - You can enhance the input validation process by checking for valid country names and handling cases where the user enters non-existent countries.

3. User Interface:
   - Currently, the user interaction is limited to the console. You can consider developing a graphical user interface (GUI) to provide a more user-friendly and intuitive way for users to input country names and view the shortest path.

4. Optimize Performance:
   - Depending on the size of the input data, there may be opportunities to optimize the performance of the program. You can analyze the code and identify areas where performance improvements can be made, such as optimizing data structures or algorithms.

Overall, I implement a road trip functionality using graph traversal algorithms. 