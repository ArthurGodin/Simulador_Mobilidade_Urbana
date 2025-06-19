package cidade;

import estruturas.HashMap1;

public class GeradorNomesRuas {
    
    // Nomes das ruas do bairro Morada do Sol, Teresina, Piauí
    private static final String[] NOMES_RUAS_MORADA_DO_SOL = {
        "Avenida Dom Severino", "Avenida João Antônio Leitão", "Avenida Presidente Kennedy", 
        "Avenida Senador Area Leão", "Conjunto Coronel Juraci Marques", "Conjunto Santa Isabel",
        "Loteamento Recanto das Estrelas", "Loteamento Residencial Pasargada da Nirvana", 
        "Parque Campestre", "Praça Balduíno Barbosa de Deus", "Praça Engenheiro Adolfo Uchôa Neto",
        "Praça Francisco das Chagas Oliveira", "Praça Jéssica Nogueira Lima", "Praça Leda Torquato",
        "Rua Antonio Bona", "Rua Armando Burlamaqui", "Rua Assis Veloso", "Rua Begonias",
        "Rua Bonifácio Abreu", "Rua Camélias", "Rua Carmélia", "Rua Cônego Raimundo Fonseca",
        "Rua Confrontação", "Rua Coronel César", "Rua Cravos", "Rua Crescêncio Ferreira",
        "Rua Crisântemos", "Rua Dálias", "Rua Deputado José Lourenço Mourão", 
        "Rua Desembargador Helvídio Aguiar", "Rua Desembargador Turenne Ribeiro", "Rua Dezesseis",
        "Rua Dezessete", "Rua Dona Amália Castro", "Rua Doutor Arnaldo Neiva", 
        "Rua Doutor Gilson Serra e Silva", "Rua Doutor José Auto de Abreu", 
        "Rua Doutora Lia Rachael Rêgo Monteiro Mendes", "Rua Elias de Oliveira e Silva",
        "Rua Empresário José Ferreira", "Rua Engenheiro Ronald Carvalho", "Rua Flores",
        "Rua Heli Castelo Branco", "Rua Helvídio Aguiar", "Rua Jaime da Silveira",
        "Rua João Borges de Sousa", "Rua José de Lima", "Rua José Torquato Viana",
        "Rua Leonardo Castelo Branco", "Rua Leôncio Ferraz", "Rua Líbia", 
        "Rua Lucílio de Albuquerque", "Rua Luísa Amélia Brandão", "Rua Major Manoel Lopes",
        "Rua Major Sebastião Saraiva", "Rua Médico Adail Monteiro Santana", "Rua Mundinho Ferraz",
        "Rua Nilo Brito", "Rua Odílio Falcão", "Rua Papoulas", "Rua Paulo Cunha",
        "Rua Poeta Carlos Drumond", "Rua Professor Odilo Ramos", "Rua Professor Vidal Ferreira",
        "Rua Professora Adalgisa Paiva", "Rua Senador Luís Mendes Ribeiro Gonçalves", 
        "Rua Simone", "Rua Teófilo dos Santos", "Rua Trevos", "Rua Treze",
        "Rua Valdemar Martins", "Vila Morada do Sol", "Rua Carlos Fortes de Pádua",
        "Rua Antônia Myrian Eduardo Pereira", "Rua Antonieta Ferraz", "Rua Antônio Maria de Sousa Fortes",
        "Rua Aristides Saraiva de Almeida", "Rua Augusto Castro", "Rua Azar Chaib",
        "Rua César Leal", "Rua Cinéas Veloso", "Rua Abimael Soares da Rocha", "Rua Américo Castelo Branco"
    };
    
    private static HashMap1<Long, String> mapeamentoNomes = new HashMap1<>();
    private static boolean inicializado = false;
    
    // Inicializa o mapeamento de nomes se ainda não foi feito
    private static void inicializar() {
        if (!inicializado) {
            inicializado = true;
        }
    }
    
    // Retorna um nome de rua baseado no ID do vértice
    public static String obterNomeRua(long id) {
        inicializar();
        
        // Verifica se já existe um nome mapeado para este ID
        String nomeExistente = mapeamentoNomes.get(id);
        if (nomeExistente != null) {
            return nomeExistente;
        }
        
        // Gera um nome baseado no ID usando módulo para garantir consistência
        // Usa os nomes reais das ruas do bairro Morada do Sol, Teresina, PI
        int indice = (int) (Math.abs(id) % NOMES_RUAS_MORADA_DO_SOL.length);
        String nome = NOMES_RUAS_MORADA_DO_SOL[indice];
        
        // Armazena o mapeamento para uso futuro
        mapeamentoNomes.put(id, nome);
        
        return nome;
    }
    
    // Método para limpar o cache (útil para testes)
    public static void limparCache() {
        mapeamentoNomes = new HashMap1<>();
        inicializado = false;
    }
}
