package io.newm.shared.public.constants

object WalletConnectConstants {
    private const val COINBASE: String = "fd20dc426fb37566d803205b19bbc1d4096b248ac04548e3cfb6b3a38bd033aa"
    private const val TRUST_WALLET = "4622a2b2d6af1c9844944291e5e7351a6aa24cd7b23099efac1b2fd875da31a0"
    private const val META_MASK = "c57ca95b47569778a828d19178114f4db188b89b763c899ba0be274e97267d96"
    private const val BLOCKCHAIN_COM = "84b43e8ddfcd18e5fcb5d21e7277733f9cccef76f7d92c836d0e481db0c70c04"
    private const val CRYPTO_COM = "f2436c67184f158d1beda5df53298ee84abfc367581e4505134b5bcf5f46697d"
    private const val RAINBOW = "1ae92b26df02f0abca6304df07debccd18262fdf5fe82daa81593582dac9a369"
    private const val UNISWAP = "c03dfee351b6fcc421b4494ea33b9d4b92a984f87aa76d1663bb28705e95034a"
    fun getSupportedWallets(): List<String> = listOf(
        COINBASE, TRUST_WALLET, META_MASK, BLOCKCHAIN_COM, CRYPTO_COM, RAINBOW, UNISWAP,
    )
}