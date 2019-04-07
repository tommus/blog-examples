package co.windly.materialanimation.presenter

import co.windly.limbo.presenter.queue.LimboQueuePresenter

class MainPresenter : LimboQueuePresenter<MainView>() {

  //region Edit

  fun onEditButtonClicked() = onceViewAttached {

    // Toggle edit.
    it.toggleEdit()
  }

  //endregion

  //region Save

  fun onSaveButtonClicked() = onceViewAttached {

    // Toggle edit.
    it.toggleEdit()

    // Retrieve edited value.
    val edited = it.retrieveEditedText()

    // Update content.
    it.changeContent(edited)
  }

  //endregion
}
