package com.android.technicalandroidnewsfeedstest.ws_models

data class NewsFeedModel(
        var title: String, // About Canada
        var rows: List<Row>
) {
    data class Row(
            var title: String, // Language
            var description: String, // Nous parlons tous les langues importants.
            var imageHref: Any // null
    )
}