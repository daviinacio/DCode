using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class Error {
        public static void NonSelectedFileError() {
            switch (Program.lang) {
                case 0: Console.WriteLine("No file selected, enter \"file open (Local file without spaces)\""); break;
                case 1: Console.WriteLine("Nenhum arquivo selecionado, digite \"file open (Local do arquivo sem espaços)\""); break;
            }
        }

        public static void InvalidCommand() {
            switch (Program.lang) {
                case 0: Console.WriteLine("Command not found"); break;
                case 1: Console.WriteLine("Comando não encontrado"); break;
            }

        }
    }
}
