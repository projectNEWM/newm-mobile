package io.newm.shared.public.models.mocks

import io.newm.shared.public.models.User

val mockUsers: List<User> = listOf(
    User(id = "1",
        createdAt = "Today",
        firstName = "Joe",
        lastName = "Blow",
        nickname = "Joey the Blower",
        pictureUrl = "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg",
        bannerUrl = "https://www.sonypictures.com/sites/default/files/styles/max_360x390/public/banner-images/2020-04/stepbrothers_banner_2572x1100_v2.png?h=abc6acbe&itok=7m7ZKbdz",
        email = "joe@blow.com"
    )
)