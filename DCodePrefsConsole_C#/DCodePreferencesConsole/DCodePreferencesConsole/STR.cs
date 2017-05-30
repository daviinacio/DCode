using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DCodePreferencesConsole {
    class STR {
        // String ID
        public static int NonSelectedFileError = 0, InvalidCommand = 1, OpenWithoutCommand = 2, NeedEntryFileName = 3;
        // Lang ID
        public static int EN = 0, PT = 1;
        // Variables
        public static int lang = EN;

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
            new String[]{ "You need entry the file name with out spaces" , "Você precisa colocar o nome do arquivo sem espaços"}
        };

        // Methods
        public static String get(int id) {
            return str[id][lang];
        }
    }
}
