package com.ff.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch

private const val ARG_PAGENO = "pageno"

class BestUnknownTeamFragment: Fragment() {

    lateinit var players: ArrayList<Player>
    lateinit var viewModel: MainViewModel
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = MainViewModel()
        players = arrayListOf()

        db = AppDatabase.getInstance(requireContext())!!

        return ComposeView(requireContext()).apply {
            setContent {
                val players: List<Player>? by viewModel.players.observeAsState()
                players?.let { printPlayers(players = it) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_PAGENO) }?.apply {
            lifecycleScope.launch { //Main
                //IO
                players = db.PlayerDao().getPlayersWeek(getInt(ARG_PAGENO)) as ArrayList<Player>
                //Main
                viewModel.players.setValue(players)
            }
        }
    }

    @Composable
    fun printPlayers(players: List<Player>) {

        LazyColumn() {
            item(players.size) {  //LazyColumn does not provide composable content, so we wrap it inside an Item
                for (player in players) {
                    PlayerRow(player = player)
                }
            }
        }
    }

    @Composable
    fun PlayerRow(player: Player) {
        Divider(color = Color.Gray, thickness = 0.5.dp)
        Card(
            shape = RectangleShape
            )
        {

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Column( horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1.0F)
                )
                {
                    Image(
                        painter = painterResource(player.getTeamImage(player.team)),
                        contentDescription = "team",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)

                    )
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Text(
                            text = player.team,
                            style = Styles.textStyle12
                        )
                        Text(
                            text = stringResource(R.string.space_dash),
                            style = Styles.textStyle12
                        )
                        Text(
                            text = player.position,
                            style = Styles.textStyle12
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(5.0F)
                    //.background(Color(0xFF551199))
                )
                {
                    Row(
                        modifier = Modifier.padding(top = 2.dp, bottom = 5.dp)
                    )
                    {
                        Text(
                            text = player.name,
                            style = Styles.textStyle14,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 30.dp, end = 0.dp)
                                .weight(5.0F)
                            )

                        Text(
                            text = getString(R.string.at_character) + player.opponent,
                            style = Styles.textStyle12,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1.0F)
                        )
                    }
                    Row( modifier = Modifier.padding(top = 2.dp, bottom = 5.dp))//verticalAlignment = Alignment.CenterVertically)
                    {


                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1.0F).padding(start = 5.dp))   //.background(
                        // Color(0xFF9FFC11))) {

                        {  Text(
                            text = "Points (STD): " + player.pointsweekstd.toString(),
                            style = Styles.textStyle12,
                            textAlign =  TextAlign.Start,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                            Text(
                                text = "Points (PPR): " + player.pointsweekppr.toString(),
                                style = Styles.textStyle12,
                                textAlign =  TextAlign.Start
                            )
                        }
                        Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1.0F).padding(end = 5.dp)) //.background(
                        {   //Color(0xFF9CCC65))) {
                            Text(
                                text = "ADP (" + player.position + getString(R.string.colon_character) + player.adpposition,
                                style = Styles.textStyle12,
                                textAlign =  TextAlign.End,
                                modifier = Modifier.padding(bottom = 5.dp)

                                //modifier = Modifier.padding(end = 10.dp) //could have used a spacer but whatever
                            )
                            Text(
                                text = "ADP (Overall): " + player.adpoverall,
                                style = Styles.textStyle12,
                                textAlign =  TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}