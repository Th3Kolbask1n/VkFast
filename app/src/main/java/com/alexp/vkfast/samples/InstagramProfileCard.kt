package com.alexp.vkfast.samples

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexp.vkfast.R


@Composable
fun InstagramProfileCard(
    model: InstagramModel,
    onFollowedButtonClickListener:(InstagramModel)-> Unit)
{


    Card(
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp,Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth()
               ,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            )
            {

                Image(

                    painter = painterResource(R.drawable.ic_instagram),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp)
                )

                UserStatistics(title = "Posts", value = "6,960")
                UserStatistics(title = "Followers", value = "436M")
                UserStatistics(title = "Following", value = "43")

            }
            Text(text = " INSTAG ${model.id}",
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive
                )
            Text(text = "${model.title}",
                fontSize = 14.sp)
            Text(text = "www.insa.com/ttw",
                fontSize = 14.sp)

            FollowButton(isFollowed = model.isFollowed){
                onFollowedButtonClickListener(model)
            }

        }
    }
}

@Composable
private fun UserStatistics(
    title:String,
    value:String
)
{

    Column(modifier = Modifier
        .height(80.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    )

    {

        Text(
            text = value,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive
        )

        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

    }
}

@Preview
@Composable

fun PreviewCardLight()
{
//    vkfastTheme (darkTheme = false){
//        InstagramProfileCard()
//    }
}

@Preview
@Composable

fun PreviewCardDark()
{
//    vkfastTheme(darkTheme = true) {
//        InstagramProfileCard()
//    }
}


@Composable

private fun FollowButton(
    isFollowed:Boolean,
    clickListener: ()-> Unit
)
{
    Button(onClick = {
        clickListener()
    },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isFollowed){ MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)}
            else
            {
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        val text = if(isFollowed)
        {
            "Unfollow"
        }
        else
        {
            "Follow"
        }
        Text(text = text)
    }
}