# Gitlet Design Document

**Name**: Aniruddh Khanwale

## Classes and Data Structures

###Blob
####Fields:
This class is designed to store a blob, i.e. a reference to a particular file
1. `String blobName` The name of the (serialized) file to which this blob refers to. 
2. `String hash` The SHA-1 hash value of the blob, generated from its contents. 
###Commit
This class is designed to store a commit, with various pointers and metadata.
####Fields:
1. `String commitID` The SHA-1 hash value of the commit.
2. `Time commit_time` The Unix epoch time when this commit was made.
3. `String commitMessage` The commit message of this commit. 
4. `HashMap blobs` The blobs contained in this commit.
##Staging Area
This  class contains all the pertinent data for the staging area. This means that includes references to various blobs. 
####Fields:
`StagingArea parent` This field stores the state of the staging area at the time of the last commit. When a commit is made, this information is updated. 
### Repository
This is an abstract class which is implemented by the master branch, and by the branch class
1. `LinkedList tree` The tree of commits
### Master
This class extends the abstract Repository class. It contains references including HEAD and various other information.
### Branch
This class extends the abstract Repository class. It contains references including HEAD and various other information.

## Algorithms
### Main Class
1. `main(String[] args)` The main method. This takes in a sring of arguments, and based on the flag of the first argument, transfers control to a method or class. 
2.`init()` Initializes a gitlet repository by creating a `.gitlet` directory. It also calls the commit method with an initial commit message.
3.`add(String[] args)` Adds the given file to the Staging Area by calling the add method of the staging area. Does nothing if the current stage contains the file already. 
4. `commit(String[] args)` Creates a new Commit with the use of the Commit class
5. `remove(String[] args)` Removes the specified file from the working directory and the staging area. 
6. `log(String[] args)` Starting from the head commit, it uses the pointers in the LinkedList object to print out the commit information as specified by the user. 
7. `branch (String[] args)` Creates a new branch instance, using the procedure outlined in the Branch class.
8. `checkout (String[] args)` Switches to the branch/commit specified by the user. It will also potentially update the HEAD reference, if the repository is being switched to a new commit or branch. 
### Commit Class
1. `getData()` This method will get all the blobs stored in the staging area, and add them to the commit. It will also clear the information in the staging area. Clearing the staging area will be defined as setting its
`parent` Staging Area to the state of the Staging Area at the time of the commit, and then clearing all the data.  
## Persistence
In order to implement persistence, all our data lives in the `.gitlet` directory. Commits, with their references to blobs, can be used to look up specific data. Blobs will be serialized such that they end in a .blob extension, commits such that they end in a .commit extension. We will also have a log file, which is responsible for storing the metadata of all previous logs. 
Each kind of object will live in a subdirectory of the `.gitlet` directory, and this will  be used to access persistence. 
