package ayds.winchester.songinfo.moredetails.model.repository.local

import android.content.Context
import ayds.winchester.songinfo.moredetails.model.entities.Description
import ayds.winchester.songinfo.moredetails.model.repository.local.sqldb.DataBase
import ayds.winchester.songinfo.moredetails.view.OtherInfoWindow

interface LocalRepository {
    fun updateArtistTerm(id : String)
    fun getArtistDescription(artistName:String): Description?
    fun saveDescriptionInDataBase(artistDescription: Description)
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

     override fun getArtistDescription(artistName:String): Description?{
         this.artistName = artistName
         return dataBase.getInfo(dataBase, artistName)
    }

    override fun saveDescriptionInDataBase(artistDescription: Description){
        dataBase.saveArtist(artistName, artistDescription.description,artistDescription.id)
    }
}