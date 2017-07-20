#include <iostream>
#include <string>

#include "console_color.h"


#define STR_LENGTH 20
#define ARRAY_LENGTH 5

// Resource text color
#define ERROR_COLOR color_red
#define ARRAY_C_COLOR color_yellow
#define ARRAY_I_COLOR color_white
#define ARRAY_T_COLOR color_lightGray

// String
#define S_CURSOR 0
#define S_FILE_CREATED 1

// Help
#define A_HELP 0
#define A_HELP_FILE 1

// Error
#define E_COMMAND_NOT_FOUND 0
#define E_COMMAND_INCOMPLET 1

using namespace std;

class Resource {
	public:
		Resource();
		string getString(int id);
		string* getArray(int id);
		void showArray(int id, bool showIndex);
		void showArray(int id);
		void showError(int id);
		void showString(int color, int id);
	private:
		string strs[STR_LENGTH];
		string arrays[ARRAY_LENGTH][STR_LENGTH];
		string errors[STR_LENGTH];
};

Resource::Resource(){
	// String
	this->strs[0] = "> ";
	this->strs[1] = "File created";
	
	this->errors[0] = "Command not found.";
	this->errors[1] = "This command need complement.";
	this->errors[2] = "Text 1";
	this->errors[3] = "Text 1";
	
	// Arrays
	this->arrays[0][00] = "add               Add a preference.";
	this->arrays[0][01] = "set               Set the value of a preference.";
	this->arrays[0][02] = "get               Get the value of a preference.";
	this->arrays[0][03] = "remove            Remove a reference.";
	this->arrays[0][04] = " ";
	this->arrays[0][05] = "file              File options. 'file --help' to more.";
	this->arrays[0][06] = " ";
	this->arrays[0][07] = "dir               Show all files and folders of diretory.";
	this->arrays[0][ 8] = "cd                Change the current diretory.";
	this->arrays[0][ 9] = " ";
	this->arrays[0][10] = "clear             Clear the console.";
	this->arrays[0][11] = "close or exit     Close console.";
	this->arrays[0][12] = " ";
	this->arrays[0][13] = "<c> '*' or 'all'  Select all. Works on 'get' and 'remove'.";
	
	this->arrays[1][00] = "create            Create a preference file.";
	this->arrays[1][01] = "delete            Delete the preference file.";
	this->arrays[1][02] = "open              Open a preference file.";
	this->arrays[1][03] = "save              Save the file.";
	this->arrays[1][04] = "close             Close the file.";
	this->arrays[1][05] = "reload            Reload the file.";
}

string Resource::getString(int id){
	return strs[id];
}

void Resource::showString(int color, int id){
	cc_print(color, this->strs[id]);
}

string* Resource::getArray(int id){
	return this->arrays[id];
}

void Resource::showArray(int id){
	showArray(id, false);
}

void Resource::showArray(int id, bool showIndex){
	string array[STR_LENGTH] = this->arrays[id];
	string prefix = "    ";
	
	for(int i = 0; !array[i].empty(); i++){
		cout << prefix;
		if(showIndex){
			cc_print(ARRAY_C_COLOR, "[");
			cc_print(ARRAY_I_COLOR, i, ((int) array->length()/10) - 1);
			cc_print(ARRAY_C_COLOR, "] ");
		}
		cc_print(ARRAY_T_COLOR, array[i] + "\n");
	}
}

void Resource::showError(int id){
	cc_print(ERROR_COLOR, this->errors[id]);
}

Resource R = Resource();


