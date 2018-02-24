#include <windows.h>
#include <iostream>

#include <dcode.h>

using namespace std;

int main(int argc, char** argv) {
	DCode d = DCode();
}




/*typedef void (*MyClass)(void);

int main(int argc, char** argv) {
	
	HINSTANCE hinst;
	MyClass DCode;
	
	hinst = LoadLibrary("DCode.dll");
	
	if(hinst){
		
		DCode = (MyClass) GetProcAddress(hinst, "DCode");
		
		//DCode d = DCode();
		
	} else {
		cout << "ERROR" << endl;
	}
	
	
	//DCode d();
	system("PAUSE");
	return 0;
}*/
