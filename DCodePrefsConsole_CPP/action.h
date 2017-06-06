#include <iostream>
#include <string>

#include "console.h"// This already includes resources

using namespace std;

// File
void a_file_create(){
	if(!c_cinEnpty()){
		string fileName = c_getLine();
		cc_print(color_red, "'" + fileName + "'");
		R.showString(color_green, S_FILE_CREATED);
	} else
		R.showError(E_COMMAND_INCOMPLET);
	//cout << "Create file" << endl;
	//string fileName = c_getLine();
	//cout << fileName;
}
