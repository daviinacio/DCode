#include "dcode.h"
#include <windows.h>

using namespace std;

// Constructors
DCode::DCode(){
	//MessageBox(0, "Hello World from DLL!\n","Hi",MB_ICONINFORMATION);
	this->open = '<'; this->space = ';'; this->close = '>';
}

DCode::DCode(DCodeMode mode){
	switch(mode){
		case NORMAL: this->open = '<'; this->space = ';'; this->close = '>'; break;
		case ARRAY: this->open = '['; this->space = ':'; this->close = ']'; break;
		case FILE: this->open = '{'; this->space = '_'; this->close = '}'; break;
		case DATE: this->open = '('; this->space = '/'; this->close = ')'; break;
		case SMALL: this->open = '.'; this->space = ','; this->close = ';'; break;
	}
}

// Destructors
DCode::~DCode(){
	
}

// Functions

// enCode and unCode
string DCode::enCode(list<string> input){
	
}

list<string> DCode::unCode(string input){
	list<string> output = list<string>();
	string word;
	
	for(int i = 0, j = 0; i < input.length(); i++){
		
		if(input[i] == open){
			// increment the oppened count
			j++;
			// include the open operator on a sub container
			if(j > 1) word += input[i];
			
		} else if(input[i] == space){
			// include the space operator on a sub container
			if(j > 1)word += input[i];
			// put the word value on output list
			else if(j == 1) output.push_back(word);
			
			// debug
			cout << word << endl;
			
			// crear the word buffer
			word = "";
			
		} else if(input[i] == close){
			// include the close operator on a sub container
			if(j > 1) word += input[i];
			// decrement the oppened count
			j--;
			
		} else {
			// put the current character on word buffer
			if(j >= 1) word += input[i];
		}
	}
	
	return output;
}


// DLL Functions
/*BOOL WINAPI DCode(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved){
	switch(fdwReason){
		case DLL_PROCESS_ATTACH: break; 
		case DLL_PROCESS_DETACH: break; 
		case DLL_THREAD_ATTACH: break; 
		case DLL_THREAD_DETACH: break;
	}
	
	// Return TRUE on success, FALSE on failure 
	return TRUE;
}*/
