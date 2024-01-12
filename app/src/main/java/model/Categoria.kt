package model

enum class Categoria {
    FRUTAS,
    VERDURAS,
    CARNES,
    LACTEOS,
    PANADERIA,
    CEREALES_Y_GRANOS,
    BEBIDAS,
    CONGELADOS,
    SNACKS;

    companion object {
        private val categorias = mutableSetOf<String>()

        fun agregarCategoria(categoria: String) {
            categorias.add(categoria.uppercase())
        }

        fun obtenerCategorias(): Set<String> {
            return (enumValues<Categoria>().map { it.name } + categorias).toSet()
        }
    }
}

