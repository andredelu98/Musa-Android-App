package it.polito.musaapp.Frontend


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.RefreshSavedReference
import it.polito.musaapp.Backend.RemoveImageInSaved
import it.polito.musaapp.R
import it.polito.musaapp.Screens



@Composable
fun SavedReference(navController: NavController, vm:MusaViewModel) {
    RefreshSavedReference(vm)
    //val storageRef = FirebaseStorage.getInstance().getReference("ReferenceTask1Arte")
    val list by vm.savedRef.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier.size(35.dp).clickable {
                    navController.navigate(Screens.ProfilePage.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            Image(
                painter = painterResource(id = R.drawable.loghetto),
                contentDescription = null,
                modifier = Modifier.size(85.dp)
            )

            Box(modifier = Modifier.size(40.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(top = 8.dp)
        )
        {
            Row {
                Text(
                    text = "REFERENCES SALVATE",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.3f)
                    .padding(top = 8.dp, end = 16.dp)
            ) {
                Row(modifier = Modifier.clickable {  }){
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Filtri",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
         /*   if(list.isNullOrEmpty()){
                IndicatorReference()
            }
            else{*/
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    list?.forEachIndexed { index, imageUrl ->
                        item(index) {
                            if(!imageUrl.equals("RIMOSSO")){
                                ImageWithHeartSaved(imageUrl = imageUrl, vm, navController)
                            }

                        }
                    }
                }
           // }

        }
    }


}
@Composable
fun ImageWithHeartSaved(imageUrl: String, vm: MusaViewModel, navController: NavController) {
    var isLiked by remember { mutableStateOf(true) }

    if(!isLiked){
        //Log.d("REFERENCE", "sono in not liked ref salvate")
        RemoveImageInSaved(imageUrl, vm)
        /*navController.navigate(Screens.SavedReference.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }*/
    }
    else{
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        )
        {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable {
                        isLiked = !isLiked
                    }
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}




