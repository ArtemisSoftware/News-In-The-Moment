package presentation.headlines.composables

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import domain.models.Article
import domain.models.ArticleType
import util.CursorUtils.handCursor

@Composable
fun ArticleCard(
    article: Article,
    imageLib: HashMap<String?, ImageBitmap> = HashMap(),
    updateImageLib: (String, ImageBitmap?) -> Unit,
    type: ArticleType,
    modifier: Modifier = Modifier,
    imageSize: Dp = 200.dp,
    onClick: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var lastIndexTitle = 0
    var lastIndexDescription = 0

    Surface(
        modifier = modifier
            .pointerHoverIcon(handCursor())
            .clickable {
                onClick.invoke(article.postUrl)
            },
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArticleImage(
                type = type,
                content = {
                    AsyncImage(
                        imageLib = imageLib,
                        imageUrl = article.imageUrl,
                        updateImageLib = updateImageLib,
                        modifier = Modifier.size(imageSize),
                    )
                },
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SuggestionChip(
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.onTertiary,
                    ),
                    onClick = {},
                    label = {
                        Text(
                            text = article.source,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                )
                Column {
                    Text(
                        text = article.title,
                        fontSize = when (type) {
                            ArticleType.HEADLINE -> 20.sp
                            ArticleType.STANDARD -> 14.sp
                        },
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            try {
                                lastIndexTitle = textLayoutResult.getLineEnd(
                                    lineIndex = 1,
                                    visibleEnd = true,
                                )
                            } catch (e: IllegalArgumentException) {}
                        },
                    )

                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                    ) {
                        if ((lastIndexTitle + 1) < article.title.length && lastIndexTitle != 0) {
                            Text(
                                text = article.title.substring(lastIndexTitle + 1),
                                fontSize = when (type) {
                                    ArticleType.HEADLINE -> 20.sp
                                    ArticleType.STANDARD -> 14.sp
                                },
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                maxLines = Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                Column {
                    Text(
                        text = article.content,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraLight,
                        maxLines = 2,
                        overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            try {
                                lastIndexDescription = textLayoutResult.getLineEnd(
                                    lineIndex = 1,
                                    visibleEnd = true,
                                )
                            } catch (e: IllegalArgumentException) {}
                        },
                    )

                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                    ) {
                        if ((lastIndexDescription + 1) < article.content.length && lastIndexDescription != 0) {
                            Text(
                                text = article.content.substring(lastIndexDescription + 1),
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraLight,
                                maxLines = Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

                FilledIconButton(
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(20.dp),
                    onClick = {
                        expanded = !expanded
                    },
                    content = {
                        Icon(
                            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Localized description",
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ArticleImage(
    type: ArticleType,
    content: @Composable () -> Unit,
) {
    when (type) {
        ArticleType.HEADLINE -> {
            Surface(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(20.dp),
            ) {
                content.invoke()
            }
        }
        ArticleType.STANDARD -> {
            content.invoke()
        }
    }
}

@Preview
@Composable
private fun ArticleCardPreview() {
    ArticleCard(
        article = DummyData.article,
        type = ArticleType.HEADLINE,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {},
        updateImageLib = { _, _ -> },
    )
}
