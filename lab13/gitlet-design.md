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

### Repository
This is the class which contains all the commits, having a tree structure
1. `LinkedList tree` The tree of commits 
## Algorithms
### Main Class
1. `main(String[] args)` The main method. This takes in a sring of arguments, and based on the flag of the first argument, transfers control to a method or class. 
2. `add(String[] args)` Initializes a gitlet repository by creating a `.gitlet` directory. It also calls the commit method with an initial commit message.
3. `commit(String[] args)` Creates a new Commit with the use of the Commit class

## Persistence
In order to implement persistence, all our data lives in the `.gitlet` directory. Commits, with their references to blobs, can be used to look up specific data. 
