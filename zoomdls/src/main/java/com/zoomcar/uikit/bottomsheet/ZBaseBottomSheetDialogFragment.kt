package com.zoomcar.uikit.bottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zoomcar.zoomdls.R
import com.zoomcar.zoomdls.databinding.LayoutBottomSheetFragmentBinding

open class ZBaseBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    protected lateinit var binding: LayoutBottomSheetFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet_fragment, container, false)

        //set to adjust screen height automatically, when soft keyboard appears on screen
        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.imageClose.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ZBottomSheetDialogStyle)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val createdDialog = super.onCreateDialog(savedInstanceState)
        createdDialog.setOnKeyListener { _: DialogInterface?, keyCode: Int, keyEvent: KeyEvent ->
            // getAction to make sure this doesn't double fire
            if (keyCode == KeyEvent.KEYCODE_BACK &&
                    keyEvent.action == KeyEvent.ACTION_UP) {
                dismiss()
                return@setOnKeyListener true
            }
            false
        }

        createdDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet: FrameLayout? = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { _bottomSheet ->
                BottomSheetBehavior.from<View>(_bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return createdDialog
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_close -> {
                onBottomSheetClosed()
                activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                dismiss()
            }
        }
    }

    open fun onBottomSheetClosed() {
    }
}