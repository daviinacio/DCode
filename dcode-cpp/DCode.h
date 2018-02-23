#ifndef DCODE_H
#define DCODE_H

#include <iostream>
#include <string>
#include <list>

#if BUILDING_DLL
#define DLLIMPORT __declspec(dllexport)
#else
#define DLLIMPORT __declspec(dllimport)
#endif

using namespace std;

class DLLIMPORT DCode {
	public:
		// Modes
		enum DCodeMode { NORMAL, ARRAY, FILE, DATE, SMALL };
		
		// Variables
		
		// Constructors
		DCode();
		DCode(DCodeMode mode);
		DCode(char open, char space, char close);
		
		// Destructors
		~DCode();
		
		// Functions
		
		// enCode and unCode
		list<string> unCode(string input);
		string enCode(list<string> input);
		
	protected:
		// Variables
		char open, space, close;
};

#endif
