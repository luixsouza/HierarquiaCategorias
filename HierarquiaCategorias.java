import java.util.ArrayList;
import java.util.List;

class Categoria {
    int id;
    String nome;
    String palavrasChave;
    int paiId;

    public Categoria(int id, String nome, String palavrasChave, int paiId) {
        this.id = id;
        this.nome = nome;
        this.palavrasChave = palavrasChave;
        this.paiId = paiId;
    }
}

public class HierarquiaCategorias {
    private List<Categoria> categorias;

    public HierarquiaCategorias(Object[][] data) {
        categorias = new ArrayList<>();

        for (Object[] row : data) {
            int id = (int) row[0];
            String nome = (String) row[1];
            String palavrasChave = (String) row[2];
            int paiId = (int) row[3];

            Categoria categoria = new Categoria(id, nome, palavrasChave, paiId);
            categorias.add(categoria);
        }
    }

    public int nivel(int categoriaId) {
        int nivel = 1;
        Categoria categoria = getCategoriaPorId(categoriaId);

        while (categoria != null && categoria.paiId != -1) {
            nivel++;
            categoria = getCategoriaPorId(categoria.paiId);
        }

        return nivel;
    }

    public List<String> categoriasPorPalavraChave(String palavraChave) {
        List<String> resultado = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (categoria.palavrasChave.contains(palavraChave)) {
                resultado.add(String.valueOf(categoria.id));
                resultado.add(String.valueOf(nivel(categoria.id)));
                resultado.add(categoria.nome);
                resultado.add(palavraChave);
            }
        }

        return resultado;
    }

public List<String> obter(int categoriaId) {
    List<String> resultado = new ArrayList<>();
    Categoria categoria = getCategoriaPorId(categoriaId);

    resultado.add(String.valueOf(nivel(categoriaId)));
    
    resultado.addAll(List.of(categoria.palavrasChave.split(", ")));

    int paiId = categoria.paiId;
    while (paiId != -1) {
        Categoria paiCategoria = getCategoriaPorId(paiId);
        resultado.addAll(List.of(paiCategoria.palavrasChave.split(", ")));
        paiId = paiCategoria.paiId;
    }

    return resultado;
}

    private Categoria getCategoriaPorId(int categoriaId) {
        for (Categoria categoria : categorias) {
            if (categoria.id == categoriaId) {
                return categoria;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Object[][] data = {
            {1, "Raiz", "Produtos", -1},
            {2, "Móveis", "Móveis", 1},
            {3, "Eletrônicos", "Eletrônicos, Gadgets", 1},
            {4, "Casa e Eletrodomésticos", "Casa, Eletrodomésticos", 1},
            {5, "Eletrodomésticos Principais", "", 4},
            {6, "Eletrodomésticos Secundários", "", 4},
            {7, "Gramado e Jardim", "Gramado, Jardim", 4},
            {8, "Eletrodomésticos de Cozinha", "", 5},
            {9, "Eletrodomésticos em Geral", "", 5},
            {10, "Veículos", "Veículos, Transporte, Carro, Moto", -1},
            {11, "Carros", "", 10}
        };

        HierarquiaCategorias hierarquia = new HierarquiaCategorias(data);

        // Exemplos de uso
        System.out.println("Nível da categoria: " + hierarquia.nivel(11));
        System.out.println("Categorias por palavra-chave: " + hierarquia.categoriasPorPalavraChave("Veículos"));
        System.out.println("Dados da categoria: " + hierarquia.obter(11));
    }
}
