package ayds.winchester.songinfo.moredetails.model.repository.local

import android.content.Context
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.repository.local.sqldb.DataBase
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow

interface LocalRepository {
    fun getCards(artistName:String): List<Card>
    fun saveCards(cards : List<Card>, artistName:String)
}
internal class LocalRepositoryImpl(private var otherInfoView : OtherInfoWindow) : LocalRepository{
    private lateinit var dataBase: DataBase

    init{
        initProperties()
    }

    private fun initProperties(){
        dataBase = DataBase(otherInfoView as Context)
    }

     override fun getCards(artistName:String) : List<Card>{
         return dataBase.getInfo(dataBase, artistName)
    }

    override fun saveCards(cards : List<Card>, artistName:String){
        for(i in cards.indices) {
            dataBase.saveArtist(artistName, cards[i].description, cards[i].infoUrl, cards[i].source.ordinal, cards[i].sourceLogoUrl)
        }
    }
}