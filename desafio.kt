enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario(val id:String, var nome:String, var pontuacao:Int = 0){
    override fun equals(other : Any?) : Boolean{
        return other is Usuario && this.id.equals(other.id)
    }
    
    fun pontuar(nivel: Nivel){
        when(nivel){
            Nivel.BASICO -> this.pontuacao += 5
            Nivel.INTERMEDIARIO -> this.pontuacao += 10
            Nivel.DIFICIL -> this.pontuacao += 15
        }
    }
}

fun registrarUsuario(usuario:Usuario?, dataSource: MutableList<Usuario>){
    if(usuario === null) throw NullPointerException()
    if(dataSource.contains(usuario)) throw IllegalArgumentException("Usuário já cadastrado no sistema")
    else dataSource.add(usuario)
}

data class ConteudoEducacional(var nome: String, val duracao: Int = 60)

//usei var somente para fazer uso do padrão builder usando o método apply (quis brincar com o recurso)
data class Formacao(var id : Int = 0,
                    var nome: String = "",
                    var nivel:Nivel = Nivel.BASICO, 
                    var conteudos: List<ConteudoEducacional> = listOf()) {
    
    val inscritos = mutableListOf<Usuario>()
    
    fun matricular(usuario: Usuario?) {
    	usuario?.let{
            inscritos.add(it)
            usuario.pontuar(nivel)
            return
        }
        throw IllegalArgumentException("Usuário null")
    }
}

fun main() {
    val dataSourceUsuarios : MutableList<Usuario> = mutableListOf()
	
    //por motivo de economia de esforço, pois a implementação seria semelhante
    //ao registro de usuario, as formações estão sendo inseridas diretamente
    //no dataSourceFormacao.
    val dataSourceFormacao : MutableList<Formacao> = mutableListOf(
        Formacao().apply{
        	id = 1
            nome = "form1"
            nivel = Nivel.BASICO
            conteudos = listOf(
            	ConteudoEducacional("aula1"),
                ConteudoEducacional("aula2"),
                ConteudoEducacional("aula3")
            )
        },
        Formacao().apply{
            id = 2
            nome = "form2"
            nivel = Nivel.INTERMEDIARIO
            conteudos = listOf(
            	ConteudoEducacional("aula1"),
                ConteudoEducacional("aula2"),
                ConteudoEducacional("aula3")
            )
        },
        Formacao().apply(){
            id = 3
            nome = "form3"
            nivel = Nivel.DIFICIL
            conteudos = listOf(
            	ConteudoEducacional("aula1"),
                ConteudoEducacional("aula2"),
                ConteudoEducacional("aula3")
            )
        }
        
    )
    
    registrarUsuario(Usuario("1", "jorge"), dataSourceUsuarios)
    
    //print para saber se o usuario foi adicionado e equals funcionando
    println(dataSourceUsuarios.contains(Usuario("2", "jorge")))
    println(dataSourceUsuarios.contains(Usuario("1", "jorge")))
    
    registrarUsuario(Usuario("2", "Antony"), dataSourceUsuarios)
    registrarUsuario(Usuario("3", "jorg"), dataSourceUsuarios)
    registrarUsuario(Usuario("4", "jor"), dataSourceUsuarios)
    registrarUsuario(Usuario("5", "jo"), dataSourceUsuarios)
    
    val formacao1 = dataSourceFormacao.get(0)
    val usuario1 = dataSourceUsuarios.get(0)
    
    println(formacao1)
    println(usuario1)
    
    formacao1.matricular(usuario1)
    
    println(usuario1.pontuacao)
    println("lista de inscritos: ${formacao1.inscritos}")
    
    println(dataSourceFormacao.get(2).matricular(usuario1))
    
    println(usuario1)
    
    formacao1.matricular(null)
}
