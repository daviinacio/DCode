using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class Error {
        public static void NonSelectedFileError() { Console.WriteLine(STR.get(STR.NonSelectedFileError)); }
        public static void InvalidCommand() { Console.WriteLine(STR.get(STR.InvalidCommand)); }
    }
}
