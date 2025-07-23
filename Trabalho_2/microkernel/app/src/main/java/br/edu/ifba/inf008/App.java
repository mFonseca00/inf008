package br.edu.ifba.inf008;

import br.edu.ifba.inf008.persistence.util.JPAUtil;
import br.edu.ifba.inf008.shell.Core;

public class App {
    public static void main(String[] args) {
        try {
            Core.init();
            
        } finally {
            JPAUtil.shutdown();
        }
    }
}
