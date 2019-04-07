package co.windly.materialanimation.presenter

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.Transition
import androidx.transition.TransitionManager
import co.windly.limbo.activity.base.LimboActivity
import co.windly.materialanimation.R
import kotlinx.android.synthetic.main.activity_main.editButtonView
import kotlinx.android.synthetic.main.activity_main.rootView
import kotlinx.android.synthetic.main.layout_card_content.cardContentView
import kotlinx.android.synthetic.main.layout_card_content.contentEditView
import kotlinx.android.synthetic.main.layout_card_content.contentView
import kotlinx.android.synthetic.main.layout_card_content.saveButtonView

class MainActivity : LimboActivity<MainView, MainPresenter>(), MainView {

  //region Ui

  override val layout: Int
    get() = R.layout.activity_main

  private fun initializeListeners() {

    // Attach edit button listener.
    editButtonView.setOnClickListener { getPresenter().onEditButtonClicked() }

    // Attach save button listener.
    saveButtonView.setOnClickListener { getPresenter().onSaveButtonClicked() }
  }

  //endregion

  //region Presenter

  override fun createPresenter(): MainPresenter =
    MainPresenter()

  //endregion

  //region Lifecycle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Snapshot constraint.
    snapshotConstraintSet()

    // Initialize listeners.
    initializeListeners()
  }

  //endregion

  //region Constraint

  private lateinit var initialRootSet: ConstraintSet

  private lateinit var initialCardSet: ConstraintSet

  private fun snapshotConstraintSet() {

    // Snapshot initial state of root constraint.
    initialRootSet = ConstraintSet()
      .also { it.clone(this, R.layout.activity_main) }

    // Snapshot initial state of card constraint.
    initialCardSet = ConstraintSet()
      .also { it.clone(this, R.layout.layout_card_content) }
  }

  private var isEditExpanded: Boolean = false

  override fun toggleEdit() {

    // Toggle value.
    isEditExpanded = !isEditExpanded

    when (isEditExpanded) {
      true -> expandEdit()
      false -> collapseEdit()
    }
  }

  override fun expandEdit() {

    // Prepare target transformation.
    val rootTargetConstraintSet = ConstraintSet()
      .also { it.clone(initialRootSet) }
      .also { it.setVisibility(editButtonView.id, View.GONE) }
      .also { it.setMargin(R.id.cardView, ConstraintSet.START, 0) }
      .also { it.setMargin(R.id.cardView, ConstraintSet.END, 0) }
      .also { it.setMargin(R.id.cardView, ConstraintSet.TOP, 0) }
      .also { it.setMargin(R.id.cardView, ConstraintSet.BOTTOM, 0) }
      .also { it.setElevation(R.id.cardView, 0.0f) }

    // Prepare target transformation.
    val cardTargetConstraintSet = ConstraintSet()
      .also { it.clone(initialCardSet) }
      .also { it.setVisibility(R.id.contentView, View.GONE) }
      .also { it.setVisibility(R.id.contentEditView, View.VISIBLE) }
      .also { it.constrainPercentWidth(R.id.editButtonView, 0.0f) }
      .also { it.constrainPercentHeight(R.id.editButtonView, 0.0f) }
//      .also { it.setVisibility(R.id.editButtonView, View.GONE) }
      .also { it.setVisibility(R.id.saveButtonView, View.VISIBLE) }

    // Prepare card expand transition.
    val cardExpandTransition = AutoTransition()
      .also { it.addListener(cardExpandTransition) }

    // Prepare transition manager.
    TransitionManager.beginDelayedTransition(rootView, cardExpandTransition)

    // Perform transition.
    rootTargetConstraintSet.applyTo(rootView)
    cardTargetConstraintSet.applyTo(cardContentView)
  }

  private val cardExpandTransition = object : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {
      // No-op.
    }

    override fun onTransitionResume(transition: Transition) {
      // No-op.
    }

    override fun onTransitionPause(transition: Transition) {
      // No-op.
    }

    override fun onTransitionCancel(transition: Transition) {
      // No-op.
    }

    override fun onTransitionStart(transition: Transition) {
      editButtonView.hide()
    }
  }

  override fun collapseEdit() {

    // Prepare target transformation.
    val rootTargetConstraintSet = ConstraintSet()
      .also { it.clone(initialRootSet) }

    // Prepare target transformation.
    val cardTargetConstraintSet = ConstraintSet()
      .also { it.clone(initialCardSet) }

    // Prepare card collapse transition.
    val cardCollapseTransition = AutoTransition()
      .also { it.addListener(cardCollapseTransition) }

    // Prepare transition manager.
    TransitionManager.beginDelayedTransition(rootView, cardCollapseTransition)

    // Perform transition.
    rootTargetConstraintSet.applyTo(rootView)
    cardTargetConstraintSet.applyTo(cardContentView)
  }

  private val cardCollapseTransition = object : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {
      // No-op.
    }

    override fun onTransitionResume(transition: Transition) {
      // No-op.
    }

    override fun onTransitionPause(transition: Transition) {
      // No-op.
    }

    override fun onTransitionCancel(transition: Transition) {
      // No-op.
    }

    override fun onTransitionStart(transition: Transition) {
      editButtonView.setImageDrawable(getDrawable(R.drawable.ic_edit_24dp))
    }
  }

  //endregion

  //region Content

  override fun changeContent(content: CharSequence) {
    contentView.text = content
  }

  //endregion

  //region Edit

  override fun retrieveEditedText(): CharSequence =
    contentEditView.text

  //endregion
}
