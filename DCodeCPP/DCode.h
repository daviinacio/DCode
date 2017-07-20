#ifndef _DCODE_H_
#define _DCODE_H_

#include <string>
#include <stdio.h>

#define UNKNOWN	   -1
#define NORMAL		0
#define DATAs		1
#define ARRAY		2
#define FILE		3
#define SMALL		4

#if BUILDING_DLL
#define DLLIMPORT __declspec(dllexport)
#else
#define DLLIMPORT __declspec(dllimport)
#endif


using namespace std;

extern "C"
class DLLIMPORT DCode {
	public:
		DCode();
		DCode(int);
		DCode(char, char, char);
		
		// enCode and unCode
		string *   unCode(string);
		string **  unCode(DCode, string);
		string *** unCode(DCode, DCode, string);
		
		string enCode(string *);
		string enCode(string **);
		string enCode(string ***);
		
		string enCode(DCode, string *);
		string enCode(DCode, string **);
		string encode(DCode, string ***);
		
		string enCode(DCode, DCode, string *);
		string enCode(DCode, DCode, string **);
		string enCode(DCode, DCode, string ***);
		
		// Internal methods
		int length(string);
		
		// Getters and Setters
		int getMode();
		char getOpen();
		char getSpace();
		char getClose();
		
		// Static methods
		static int getMode(string);
		static int countNum(string, char);
		static int length(DCode, string);
		
		// Other
		virtual ~DCode();
	private:
		char open, space, close;
};

//extern "C" __declspec(dllexport)

#endif
