

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

fun registrarUsuario(usuario:Usuario?, dataSource: MutableSet<Usuario>){
    if(usuario === null) throw NullPointerException()
    if(dataSource.contains(usuario)) throw IllegalArgumentException("Usuário já cadastrado no sistema")
    else dataSource.add(usuario)
}

data class ConteudoEducacional(var nome: String, val duracao: Int = 60)

data class Formacao(val id : Int, val nome: String, val nivel:Nivel, val conteudos: List<ConteudoEducacional>) {

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
    val dataSourceUsuarios : MutableSet<Usuario> = mutableSetOf()
	
    //por motivo de economia de esforço, pois a implementação seria semelhante
    //ao registro de usuario, as formações estão sendo inseridas diretamente
    //no dataSourceFormacao.
    val dataSourceFormacao : MutableSet<Formacao> = mutableSetOf(
        Formacao(1, form1, Nivel.BASICO),
        Formacao(2, form2, Nivel.INTERMEDIARIO),
        Formacao(3, form3, Nivel.DEFICIL),
        Formacao(4, form4, Nivel.DIFICIL),
        Formacao(5, form5, Nivel.INTERMEDIARIO),
    )
    
    registrarUsuario(Usuario("1", "jorge"), dataSourceUsuarios)
    println(dataSourceUsuarios.contains(Usuario("2", "jorge")))
    println(dataSourceUsuarios.contains(Usuario("1", "jorge")))
    
    registrarUsuario(Usuario("2", "Antony"), dataSourceUsuarios)
    registrarUsuario(Usuario("3", "jorg"), dataSourceUsuarios)
    registrarUsuario(Usuario("4", "jor"), dataSourceUsuarios)
    registrarUsuario(Usuario("5", "jo"), dataSourceUsuarios)
        
}
