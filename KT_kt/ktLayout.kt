
package com.KT_kt

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.graphics.Rect

class KtLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    /**
     * These parameters belong to each child.
     * KtLayout reads them when measuring and positioning children.
     */
    class Params : MarginLayoutParams {

        var widthP: Float = 0f
        var heightP: Float = 0f

        var topP: Float = -1f
        var leftP: Float = -1f
        var bottomP: Float = -1f
        var rightP: Float = -1f

        var centerX: Boolean = false
        var centerY: Boolean = false

        var belowId: Int = View.NO_ID
        var aboveId: Int = View.NO_ID
        var toRightOfId: Int = View.NO_ID
        var toLeftOfId: Int = View.NO_ID

        constructor() : super(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        constructor(width: Int, height: Int) : super(width, height)

        constructor(source: ViewGroup.LayoutParams) : super(source) {
            if (source is Params) {
                widthP = source.widthP
                heightP = source.heightP

                topP = source.topP
                leftP = source.leftP
                bottomP = source.bottomP
                rightP = source.rightP

                centerX = source.centerX
                centerY = source.centerY

                belowId = source.belowId
                aboveId = source.aboveId
                toRightOfId = source.toRightOfId
                toLeftOfId = source.toLeftOfId
            }
        }

        constructor(context: Context, attrs: AttributeSet) :
        super(context, attrs)
    }

    // Convert ordinary Android layout parameters into KtLayout parameters.
    override fun generateLayoutParams(
        p: ViewGroup.LayoutParams
    ): ViewGroup.LayoutParams {
        return Params(p)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return Params()
    }

    override fun generateLayoutParams(
        attrs: AttributeSet
    ): ViewGroup.LayoutParams {
        return Params(context, attrs)
    }

    override fun checkLayoutParams(
        p: ViewGroup.LayoutParams?
    ): Boolean {
        return p is Params
    }

    private fun childParams(child: View): Params {
        val current = child.layoutParams

        if (current is Params) {
            return current
        }

        val converted = if (current != null) {
            Params(current)
        } else {
            Params()
        }

        child.layoutParams = converted
        return converted
    }

    // ---------------------------------------------------------
    // MEASUREMENT
    // ---------------------------------------------------------

    // override fun onMeasure(
    //     widthMeasureSpec: Int,
    //     heightMeasureSpec: Int
    // ) {
    //     val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
    //     val heightMode = MeasureSpec.getMode(heightMeasureSpec)

    //     // When inside a ScrollView, Android may provide UNSPECIFIED height.
    //     // In that case, use the explicitly requested layout height if available.
    //     val requestedHeight = layoutParams?.height
    //     ?: LayoutParams.WRAP_CONTENT

    //     val parentHeight = if (
    //         heightMode == MeasureSpec.UNSPECIFIED &&
    //         requestedHeight > 0
    //     ) {
    //         requestedHeight
    //     } else {
    //         MeasureSpec.getSize(heightMeasureSpec)
    //     }

    //     val contentWidth = (
    //         parentWidth - paddingLeft - paddingRight
    //     ).coerceAtLeast(0)

    //     val contentHeight = (
    //         parentHeight - paddingTop - paddingBottom
    //     ).coerceAtLeast(0)

    //     var maxChildRight = 0
    //     var maxChildBottom = 0

    //     for (i in 0 until childCount) {
    //         val child = getChildAt(i)

    //         if (child.visibility == GONE) continue

    //         val p = childParams(child)

    //         val childWidthSpec = if (p.widthP > 0f) {
    //             MeasureSpec.makeMeasureSpec(
    //                 (contentWidth * p.widthP).toInt().coerceAtLeast(0),
    //                 MeasureSpec.EXACTLY
    //             )
    //         } else {
    //             getChildMeasureSpec(
    //                 widthMeasureSpec,
    //                 paddingLeft + paddingRight +
    //                 p.leftMargin + p.rightMargin,
    //                 p.width
    //             )
    //         }

    //         val childHeightSpec = if (p.heightP > 0f) {
    //             MeasureSpec.makeMeasureSpec(
    //                 (contentHeight * p.heightP).toInt().coerceAtLeast(0),
    //                 MeasureSpec.EXACTLY
    //             )
    //         } else {
    //             getChildMeasureSpec(
    //                 heightMeasureSpec,
    //                 paddingTop + paddingBottom +
    //                 p.topMargin + p.bottomMargin,
    //                 p.height
    //             )
    //         }

    //         child.measure(childWidthSpec, childHeightSpec)

    //         val estimatedLeft = when {
    //             p.leftP >= 0f ->
    //             (contentWidth * p.leftP).toInt()

    //             p.rightP >= 0f ->
    //             contentWidth -
    //             (contentWidth * p.rightP).toInt() -
    //             child.measuredWidth

    //             p.centerX ->
    //             (contentWidth - child.measuredWidth) / 2

    //             else -> 0
    //         }

    //         val estimatedTop = when {
    //             p.topP >= 0f ->
    //             (contentHeight * p.topP).toInt()

    //             p.bottomP >= 0f ->
    //             contentHeight -
    //             (contentHeight * p.bottomP).toInt() -
    //             child.measuredHeight

    //             p.centerY ->
    //             (contentHeight - child.measuredHeight) / 2

    //             else -> 0
    //         }

    //         maxChildRight = maxOf(
    //             maxChildRight,
    //             estimatedLeft + child.measuredWidth +
    //             p.leftMargin + p.rightMargin
    //         )

    //         maxChildBottom = maxOf(
    //             maxChildBottom,
    //             estimatedTop + child.measuredHeight +
    //             p.topMargin + p.bottomMargin
    //         )
    //     }

    //     val desiredWidth = maxChildRight + paddingLeft + paddingRight

    //     val desiredHeight = maxOf(
    //         maxChildBottom + paddingTop + paddingBottom,
    //         if (
    //             heightMode == MeasureSpec.UNSPECIFIED &&
    //             requestedHeight > 0
    //         ) requestedHeight else 0
    //     )

    //     val measuredWidth = resolveSize(
    //         desiredWidth,
    //         widthMeasureSpec
    //     )

    //     val measuredHeight = resolveSize(
    //         desiredHeight,
    //         heightMeasureSpec
    //     )

    //     setMeasuredDimension(measuredWidth, measuredHeight)
    // }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
        val availableHeight = MeasureSpec.getSize(heightMeasureSpec)

        val requestedWidth = layoutParams?.width ?: LayoutParams.WRAP_CONTENT
        val requestedHeight = layoutParams?.height ?: LayoutParams.WRAP_CONTENT

        // Available space for children during the first measurement.
        val firstContentWidth = (
            availableWidth - paddingLeft - paddingRight
        ).coerceAtLeast(0)

        val firstContentHeight = (
            availableHeight - paddingTop - paddingBottom
        ).coerceAtLeast(0)

        var maxChildRight = 0
        var maxChildBottom = 0

        // ---------------------------------------------------------
        // PASS 1: MEASURE CHILDREN
        // ---------------------------------------------------------

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == GONE) continue

            val p = childParams(child)

            // Percentage dimensions are temporarily zero.
            // Their actual dimensions will be resolved in pass 2.
            val childWidthSpec = if (p.widthP > 0f) {
                MeasureSpec.makeMeasureSpec(
                    0,
                    MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight +
                    p.leftMargin + p.rightMargin,
                    p.width
                )
            }

            val childHeightSpec = if (p.heightP > 0f) {
                MeasureSpec.makeMeasureSpec(
                    0,
                    MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom +
                    p.topMargin + p.bottomMargin,
                    p.height
                )
            }

            child.measure(childWidthSpec, childHeightSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val estimatedLeft = when {
                p.leftP >= 0f ->
                (firstContentWidth * p.leftP).toInt()

                p.rightP >= 0f ->
                firstContentWidth -
                (firstContentWidth * p.rightP).toInt() -
                childWidth

                p.centerX ->
                (firstContentWidth - childWidth) / 2

                else -> 0
            }

            val estimatedTop = when {
                p.topP >= 0f ->
                (firstContentHeight * p.topP).toInt()

                p.bottomP >= 0f ->
                firstContentHeight -
                (firstContentHeight * p.bottomP).toInt() -
                childHeight

                p.centerY ->
                (firstContentHeight - childHeight) / 2

                else -> 0
            }

            // Percentage-sized children do not determine
            // the natural size of a WRAP_CONTENT parent.
            if (p.widthP <= 0f) {
                maxChildRight = maxOf(
                    maxChildRight,
                    estimatedLeft + childWidth +
                    p.leftMargin + p.rightMargin
                )
            }

            if (p.heightP <= 0f) {
                maxChildBottom = maxOf(
                    maxChildBottom,
                    estimatedTop + childHeight +
                    p.topMargin + p.bottomMargin
                )
            }
        }

        // ---------------------------------------------------------
        // DETERMINE THE PARENT'S MEASURED SIZE
        // ---------------------------------------------------------

        val desiredWidth = maxChildRight + paddingLeft + paddingRight

        val desiredHeight = maxChildBottom + paddingTop + paddingBottom

        val finalDesiredWidth = maxOf(
            desiredWidth,
            if (widthMode == MeasureSpec.UNSPECIFIED &&
                requestedWidth > 0) requestedWidth else 0
        )

        val finalDesiredHeight = maxOf(
            desiredHeight,
            if (heightMode == MeasureSpec.UNSPECIFIED &&
                requestedHeight > 0) requestedHeight else 0
        )

        val finalWidth = resolveSize(
            finalDesiredWidth,
            widthMeasureSpec
        )

        val finalHeight = resolveSize(
            finalDesiredHeight,
            heightMeasureSpec
        )

        // These are now the parent's resolved dimensions.
        val finalContentWidth = (
            finalWidth - paddingLeft - paddingRight
        ).coerceAtLeast(0)

        val finalContentHeight = (
            finalHeight - paddingTop - paddingBottom
        ).coerceAtLeast(0)

        // ---------------------------------------------------------
        // PASS 2: RESOLVE PERCENTAGE DIMENSIONS
        // ---------------------------------------------------------

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == GONE) continue

            val p = childParams(child)

            // No percentage dimensions? Already measured.
            if (p.widthP <= 0f && p.heightP <= 0f) {
                continue
            }

            val childWidthSpec = if (p.widthP > 0f) {
                MeasureSpec.makeMeasureSpec(
                    (finalContentWidth * p.widthP)
                    .toInt()
                    .coerceAtLeast(0),
                    MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight +
                    p.leftMargin + p.rightMargin,
                    p.width
                )
            }

            val childHeightSpec = if (p.heightP > 0f) {
                MeasureSpec.makeMeasureSpec(
                    (finalContentHeight * p.heightP)
                    .toInt()
                    .coerceAtLeast(0),
                    MeasureSpec.EXACTLY
                )
            } else {
                getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom +
                    p.topMargin + p.bottomMargin,
                    p.height
                )
            }

            // Android now measures the child using the
            // resolved percentage dimensions.
            child.measure(
                childWidthSpec,
                childHeightSpec
            )
        }

        // Finalize the parent's measurement.
        setMeasuredDimension(
            finalWidth,
            finalHeight
        )
    }

    // ---------------------------------------------------------
    // POSITIONING
    // ---------------------------------------------------------

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        val positions = HashMap<View, Rect>()
        val resolving = HashSet<View>()

        fun findChildById(id: Int): View? {
            if (id == View.NO_ID) return null

            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child.id == id) return child
            }

            return null
        }

        // First calculate each child's requested position.
        fun resolvePosition(child: View): Rect {
            positions[child]?.let { return Rect(it) }

            val p = childParams(child)

            if (!resolving.add(child)) {
                return Rect(
                    paddingLeft + p.leftMargin,
                    paddingTop + p.topMargin,
                    paddingLeft + p.leftMargin + child.measuredWidth,
                    paddingTop + p.topMargin + child.measuredHeight
                )
            }

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val rightSibling = findChildById(p.toRightOfId)
            val leftSibling = findChildById(p.toLeftOfId)

            val leftPosition = when {
                rightSibling != null && rightSibling != child ->
                resolvePosition(rightSibling).right + p.leftMargin

                leftSibling != null && leftSibling != child ->
                resolvePosition(leftSibling).left - childWidth - p.rightMargin

                p.leftP >= 0f ->
                paddingLeft + (contentWidth * p.leftP).toInt() + p.leftMargin

                p.rightP >= 0f ->
                paddingLeft + contentWidth -
                (contentWidth * p.rightP).toInt() -
                childWidth - p.rightMargin

                p.centerX ->
                paddingLeft + (contentWidth - childWidth) / 2 +
                (p.leftMargin - p.rightMargin) / 2

                else -> paddingLeft + p.leftMargin
            }

            val belowSibling = findChildById(p.belowId)
            val aboveSibling = findChildById(p.aboveId)

            val topPosition = when {
                belowSibling != null && belowSibling != child ->
                resolvePosition(belowSibling).bottom + p.topMargin

                aboveSibling != null && aboveSibling != child ->
                resolvePosition(aboveSibling).top - childHeight - p.bottomMargin

                p.topP >= 0f ->
                paddingTop + (contentHeight * p.topP).toInt() + p.topMargin

                p.bottomP >= 0f ->
                paddingTop + contentHeight -
                (contentHeight * p.bottomP).toInt() -
                childHeight - p.bottomMargin

                p.centerY ->
                paddingTop + (contentHeight - childHeight) / 2 +
                (p.topMargin - p.bottomMargin) / 2

                else -> paddingTop + p.topMargin
            }

            val result = Rect(
                leftPosition,
                topPosition,
                leftPosition + childWidth,
                topPosition + childHeight
            )

            resolving.remove(child)
            positions[child] = result

            return Rect(result)
        }

        // Children already placed in the layout.
        val placedChildren = mutableListOf<Pair<View, Rect>>()

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            if (child.visibility == GONE) continue

            val p = childParams(child)
            val position = resolvePosition(child)

            // Determine which direction to use when an overlap occurs.
            // Explicit vertical relationships take priority.
            // Explicit horizontal relationships take priority next.
            // Otherwise, use the direction requiring the smaller movement.
            val pushHorizontally = when {
                p.belowId != View.NO_ID || p.aboveId != View.NO_ID ->
                false

                p.toRightOfId != View.NO_ID || p.toLeftOfId != View.NO_ID ->
                true

                else -> {
                    val firstOverlap = placedChildren.firstOrNull {
                        Rect.intersects(position, it.second)
                    }

                    if (firstOverlap == null) {
                        false
                    } else {
                        val other = firstOverlap.second

                        val overlapX = minOf(position.right, other.right) -
                        maxOf(position.left, other.left)

                        val overlapY = minOf(position.bottom, other.bottom) -
                        maxOf(position.top, other.top)

                        overlapX < overlapY
                    }
                }
            }

            // Keep pushing until this child no longer overlaps
            // any child that has already been placed.
            var attempts = 0

            while (attempts < childCount + 1) {
                val overlapping = placedChildren.filter {
                    Rect.intersects(position, it.second)
                }

                if (overlapping.isEmpty()) break

                if (pushHorizontally) {
                    val furthestRight = overlapping.maxOf {
                        it.second.right
                    }

                    val shift = furthestRight - position.left

                    position.offset(shift, 0)
                } else {
                    val furthestBottom = overlapping.maxOf {
                        it.second.bottom
                    }

                    val shift = furthestBottom - position.top

                    position.offset(0, shift)
                }

                attempts++
            }

            child.layout(
                position.left,
                position.top,
                position.right,
                position.bottom
            )

            placedChildren.add(child to Rect(position))
        }
    }

    // =============================================================
    // VIEW EXTENSION PROPERTIES
    // These allow children to store their own layout instructions.
    // =============================================================

    private fun View.ktParams(): KtLayout.Params {
        val current = layoutParams

        if (current is KtLayout.Params) {
            return current
        }

        val converted = if (current != null) {
            KtLayout.Params(current)
        } else {
            KtLayout.Params()
        }

        layoutParams = converted
        return converted
    }

    private fun View.updateKtParams(
        update: KtLayout.Params.() -> Unit
    ) {
        val params = ktParams()
        params.update()

        // Ask the parent to measure and position this child again.
        requestLayout()
        (parent as? View)?.requestLayout()
    }

    // Dimensions
    var View.widthP: Float
    get() = (layoutParams as? KtLayout.Params)?.widthP ?: 0f
    set(value) {
        updateKtParams { widthP = value }
    }

    var View.heightP: Float
    get() = (layoutParams as? KtLayout.Params)?.heightP ?: 0f
    set(value) {
        updateKtParams { heightP = value }
    }

    // Positions
    var View.topP: Float
    get() = (layoutParams as? KtLayout.Params)?.topP ?: -1f
    set(value) {
        updateKtParams { topP = value }
    }

    var View.leftP: Float
    get() = (layoutParams as? KtLayout.Params)?.leftP ?: -1f
    set(value) {
        updateKtParams { leftP = value }
    }

    var View.bottomP: Float
    get() = (layoutParams as? KtLayout.Params)?.bottomP ?: -1f
    set(value) {
        updateKtParams { bottomP = value }
    }

    var View.rightP: Float
    get() = (layoutParams as? KtLayout.Params)?.rightP ?: -1f
    set(value) {
        updateKtParams { rightP = value }
    }

    // Centering
    var View.centerX: Boolean
    get() = (layoutParams as? KtLayout.Params)?.centerX ?: false
    set(value) {
        updateKtParams { centerX = value }
    }

    var View.centerY: Boolean
    get() = (layoutParams as? KtLayout.Params)?.centerY ?: false
    set(value) {
        updateKtParams { centerY = value }
    }

    // Relative positioning
    var View.belowId: Int
    get() = (layoutParams as? KtLayout.Params)?.belowId ?: View.NO_ID
    set(value) {
        updateKtParams { belowId = value }
    }

    var View.aboveId: Int
    get() = (layoutParams as? KtLayout.Params)?.aboveId ?: View.NO_ID
    set(value) {
        updateKtParams { aboveId = value }
    }

    var View.toRightOfId: Int
    get() = (layoutParams as? KtLayout.Params)?.toRightOfId ?: View.NO_ID
    set(value) {
        updateKtParams { toRightOfId = value }
    }

    var View.toLeftOfId: Int
    get() = (layoutParams as? KtLayout.Params)?.toLeftOfId ?: View.NO_ID
    set(value) {
        updateKtParams { toLeftOfId = value }
    }
}

fun Context.ktLayout(
    block: KtLayout.() -> Unit = {}
): KtLayout {
    return KtLayout(this).apply {
        block()
    }
}
