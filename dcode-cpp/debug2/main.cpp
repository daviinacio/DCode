#include <iostream>
#include <windows.h>

#include <dcode.h>

//#include <../src/dcode.h>

using namespace std;

int main(int argc, char** argv) {
	DCode d = DCode();
	
	d.unCode("rrtyy<um;dois;tres;>rtyrty");
	
	return 0;
}
