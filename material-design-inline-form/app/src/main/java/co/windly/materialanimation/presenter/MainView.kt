package co.windly.materialanimation.presenter

import co.windly.limbo.LimboView

interface MainView : LimboView {

  //region Card

  fun toggleEdit()

  fun expandEdit()

  fun collapseEdit()

  //endregion

  //region Content

  fun changeContent(content: CharSequence)

  //endregion

  //region Edit

  fun retrieveEditedText(): CharSequence

  //endregion
}
