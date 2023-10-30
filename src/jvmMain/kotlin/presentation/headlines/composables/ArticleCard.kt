package presentation.headlines.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.*
import domain.models.ArticleType
import presentation.headlines.mappers.ArticleUI
import util.CursorUtils.handCursor

@Composable
fun ArticleCard(
    article: ArticleUI,
    modifier: Modifier = Modifier,
    imageSize: Dp = 200.dp,
    onClick: (String?) -> Unit,
) {
    Surface(
        modifier = modifier
            .pointerHoverIcon(handCursor())
            .clickable {
                onClick.invoke(article.url)
            },
        contentColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArticleImage(
                type = article.type,
                image = article.image,
                imageSize = imageSize,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                SuggestionChip(
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                    onClick = {},
                    label = {
                        Text(
                            text = article.source,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
                Text(
                    text = article.title,
                    fontSize = when(article.type){
                        ArticleType.HEADLINE -> 20.sp
                        ArticleType.STANDARD -> 14.sp
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.content,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraLight,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ArticleImage(
    type: ArticleType,
    image: ImageBitmap,
    imageSize: Dp
) {
    when(type){
        ArticleType.HEADLINE -> {
            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    bitmap = image,
                    "news thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize)
                )
            }
        }
        ArticleType.STANDARD -> {
            Image(
                bitmap = image,
                "news thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(imageSize)
            )
        }
    }
}

@Preview
@Composable
private fun ArticleCardPreview() {
    ArticleCard(
        article = DummyData.article,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {}
    )
}
