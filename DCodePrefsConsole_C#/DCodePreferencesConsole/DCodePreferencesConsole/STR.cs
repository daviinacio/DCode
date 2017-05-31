using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class STR {
        // String ID
        public static int NonSelectedFileError = 0, InvalidCommand = 1, OpenWithoutCommand = 2, NeedEntryFileName = 3, FileNotExists = 4, FileClosed = 5,
            NeedCloseFile = 6;
        // Lang ID
        public static int EN = 0, PT = 1;
        // Variables
        public static int lang = EN;

        private static String [][] help = {
            //           Command      Function
            new String[] {"add", "Add a preference to file.", "Adiciona uma preferencia ao arquivo"}
        };

        private static String[][] str = {
            // Error
            new String[]{
                "No file selected, enter \"file open (Local file without spaces)\"",
                "Nenhum arquivo selecionado, digite \"file open (Local do arquivo sem espaços)\""
            },
            new String[] {
                "Command not found",
                "Comando não encontrado"
            },
            // Open
            new String[]{ "You need entry other command ['open', 'save', 'close']", "Você precisa colocar outro comando ['open', 'save', 'close']" },
            new String[]{ "You need entry the file name without spaces" , "Você precisa colocar o nome do arquivo sem espaços"},
            new String[]{ "File not exists" , "O arquivo não existe"},
            new String[]{ "File closed" , "Arquivo fechado"},
            new String[]{ "You need close the file to use this command\n\n    file close" , "Você precisa fechar o arquivo pra usar esse comando\n\n file close"}
        };

        // Methods
        public static String get(int id) {
            return str[id][lang];
        }

        public static void showHelp() {
            int width = 15;
            for (int i = 0; i < help.Length; i++) {
                String[] h = help[i];
                Console.WriteLine(Program.tabulacao(h[0], width) + h[lang + 1]);
            }
        }
    }
}
