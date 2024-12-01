package common;

import java.util.Random;

import com.jsy.eng_software_urna_eletronica.controllers.User;

public class utils {
	private static final String[] FIRST_NAMES = { "Jorge", "Joao", "Henrique", "Gustavo", "Guilherme", "Ornofre", "Ian",
			"Gabriel", "Cleiton", "Sergio", "Lucas", "Pedro", "Matheus", "Rafael", "Felipe", "Bruno", "Tiago",
			"Leonardo", "Daniel", "Carlos", "Vinicius", "Andre", "Marcelo", "Victor", "Ricardo", "Eduardo", "Fernando",
			"Roberto", "Alexandre", "Diego", "Antonio", "Murilo", "Leandro", "Rodrigo", "Marcos", "Wesley", "Fabio",
			"Douglas", "Igor", "Samuel", "Jose", "William", "Nathan", "Thiago", "Everton", "Kleber", "Elton", "Luis",
			"Alex", "Cristiano", "Francisco", "Marlon", "Brayan", "Alan", "Geovanni", "Julio", "Jair", "Erick", "Edson",
			"Ronaldo", "Caio", "Otavio", "Vitor", "Afonso", "Angelo", "Matias", "Artur", "Bernardo", "Davi", "Enzo",
			"Frederico", "Heitor", "Joaquim", "Lorenzo", "Miguel", "Noah", "Benjamin", "Vicente", "Nicolas", "Tomás",
			"Alberto", "Augusto", "Sandro", "Raul", "Renato", "Sandro", "Sandro", "Rafael", "Sebastião", "Teodoro",
			"Valentim", "Walter", "Xavier", "Yuri", "Zeca", "Cesar", "Enrico", "Geraldo", "Gilberto", "Jean" };

	private static final String[] LAST_NAMES = { "Silva", "Santos", "Oliveira", "Souza", "Pereira", "Costa",
			"Rodrigues", "Almeida", "Nascimento", "Lima", "Araújo", "Fernandes", "Carvalho", "Gomes", "Martins",
			"Rocha", "Ribeiro", "Alves", "Monteiro", "Mendes", "Barros", "Freitas", "Barbosa", "Pinto", "Correia",
			"Moreira", "Cardoso", "Teixeira", "Cavalcanti", "Dias", "Castro", "Campos", "Moura", "Peixoto", "Andrade",
			"Leal", "Vieira", "Santana", "Machado", "Duarte", "Ramos", "Freire", "Amaral", "Tavares", "Matos",
			"Azevedo", "Braga", "Cunha", "Farias", "Lopes", "Macedo", "Nogueira", "Reis", "Xavier", "Branco", "Fonseca",
			"Pacheco", "Neves", "Borges", "Siqueira", "Moraes", "Mello", "Guimaraes", "Figueiredo", "Sales", "Viana",
			"Monteiro", "Ferreira", "Vargas", "Vasconcelos", "Aguiar", "Soares", "Batista", "Parreira", "Campos",
			"Assis", "Domingues", "Aragao", "Bezerra", "Bittencourt", "Carmo", "Chaves", "Coelho", "Diniz", "Espindola",
			"Esteves", "Falcao", "Farias", "Franco", "Gama", "Garcia", "Henriques", "Lacerda", "Lourenco", "Magalhaes",
			"Medeiros", "Meireles", "Meneses", "Mesquita", "Miranda" };

	private static final String[] PARTIDOS = { "MDB", "PDT", "PT", "PCdoB","PSB","PSDB","AGIR","MOBILIZA","CIDADANIA","AVANTE","MBL"};

	private static final String[] CARGOS = {"CIVIL", "SENADOR", "DEPUTADO", "GOVERNADOR", "PRESIDENTE"};
	
	private static final Random RANDOM = new Random();
	
	
	private static String gerarNome() {
	    String primeiroNome = FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
	    String sobrenome = LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
	    return primeiroNome + " " + sobrenome;
	}

	private static String gerarCargo() {
	    int[] pesos = {95, 90, 70, 20, 5};
	    int totalPeso = 0;

	    for (int peso : pesos) {
	        totalPeso += peso;
	    }

	    int valorAleatorio = RANDOM.nextInt(totalPeso) + 1;

	    int acumulado = 0;
	    for (int i = 0; i < pesos.length; i++) {
	        acumulado += pesos[i];
	        if (valorAleatorio <= acumulado) {
	            return CARGOS[i];
	        }
	    }

	    return "CIVIL"; 
	}

	private static String gerarPartido() {
	    return PARTIDOS[RANDOM.nextInt(PARTIDOS.length)];
	}

	public static User generateRandomUser() {
	    String cargo = gerarCargo();

	    String partido = cargo.equals("CIVIL") ? null : gerarPartido();

	    if (cargo == null || cargo.isBlank()) {
	        cargo = "CIVIL";
	        partido = null;
	    }

	    return new User(0, gerarNome(), partido, cargo);
	}

	
}
