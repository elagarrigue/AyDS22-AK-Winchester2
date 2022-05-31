package ayds.winchester.songinfo.moredetails.model.repository.local

import android.content.Context
import ayds.winchester.songinfo.moredetails.model.entities.Card
import ayds.winchester.songinfo.moredetails.model.entities.EmptyCard
import ayds.winchester.songinfo.moredetails.model.repository.local.sqldb.DataBase
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow

interface LocalRepository {
    fun updateArtistTerm(id : String)
    fun getArtistDescription(artistName:String): List<Card>
    fun saveDescriptionInDataBase(cards : List<Card>)
}
internal class LocalRepositoryImpl(private var otherInfoView : OtherInfoWindow) : LocalRepository{
    private lateinit var dataBase: DataBase
    private lateinit var artistName: String

    init{
        initProperties()
    }

    private fun initProperties(){
        dataBase = DataBase(otherInfoView as Context)
    }

    override fun updateArtistTerm(id : String){
        dataBase.updateArtistTerm(artistName, id)
    }

     override fun getArtistDescription(artistName:String) : List<Card>{
         this.artistName = artistName
         return dataBase.getInfo(dataBase, artistName)
    }

    override fun saveDescriptionInDataBase(cards : List<Card>){
        for(i in cards.indices) {
            if(cards[i] != EmptyCard)
                dataBase.saveArtist(artistName, cards[i].description, cards[i].infoUrl, cards[i].source.ordinal)
        }
    }
}