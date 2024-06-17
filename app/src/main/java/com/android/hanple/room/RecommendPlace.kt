package com.android.hanple.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recommend_table")
data class RecommendPlace(
    @PrimaryKey
    @ColumnInfo(name = "recommend_id")
    val id: Int,
    val name: String
)



val recommendPlaceGoogleID = listOf(
            "ChIJ40BcLnalfDURExEiVLXpe7s",
            "ChIJwY4p1Fy7fDURJCttt7vIQOA",
            "ChIJqxQ_G_GifDURDI7jheBrQzs",
            "ChIJj74QBUyifDURA5G7KLd90Bo",
            "ChIJ7bllt0ykfDURonZm_i0K2Ns",
            "ChIJlYsJBSGjfDURoz6eGXHybGw",
            "ChIJ68AiKsWYfDUROpTctLKtxtU",
            "ChIJod7tSseifDUR9hXHLFNGMIs",
            "ChIJMcWZMY2ifDUR2NLv8F3Togc",
            "ChIJpQq3J-mifDURzf9EkbTK-0E",
            "ChIJFT6cSp6xfDUR90t82LlG1F0",
            "ChIJLd1Hp_ajfDURfamTpLiDmLE",
            "ChIJV0zE7B2efDURYX7Tt_DWyw8",
            "ChIJUZmyjlmhfDUR3uBrSYR4FUU",
            "ChIJ494aXOikfDURo4AB6ZkooFI",
            "ChIJhai6ekSwfDURT3n74hJkQjY",
            "ChIJU9BpiHuhfDUR6nXlfhvNWp8",
            "ChIJY74TkgKhfDUR0W9AQpMryt0",
            "ChIJ79IzTDCefDUR2PGMH3vzeWk",
            "ChIJ7baYZ2yefDURnSOpnOdZbqs",
            "ChIJ98UyzdSkfDUR5zrvE-rIqPQ",
            "ChIJFSKvMRmefDURQRhOh2w0FtI",
            "ChIJK-qdMzmefDURt-XY8XKTUXU",
            "ChIJxbppRy2jfDURIlwDLnGy8PM",
            "ChIJhykhEpmkfDURZY1dpWRNiE0",
            "ChIJg9l1a328fDURgs4XgBuMzic",
            "ChIJX1qfHm-cfDUR9LUvKrFBjTI",
            "ChIJwWA4JJq-fDURfXF0WJ1Sy-0",
            "ChIJb34qtU6gfDUR0Ehf_K-jp3w",
            "ChIJl9TodRaifDURJnqTLlP7TJk",
            "ChIJ7fwGSoiffDURi3t1zyLY05k",
            "ChIJd2dTlACdfDUREGEuNxx5N7A",
            "ChIJE0oMKmeifDURTpYQy3MVEe4",
            "ChIJS9LmYKWlfDURcU-nZsIBtPg",
            "ChIJ5XjKGce8fDURN5WMeGswET8",
            "ChIJYTygFBG8fDURIPLGAkFtwOg",
            "ChIJd6nKUuKjfDURcFpjAL5fonw",
            "ChIJ6b6W5F2efDURmM-j3m2XuCA",
            "ChIJqyhEBr-ffDUR4h_3EsvTtlQ",
)

