/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.descriptors.components

import org.jetbrains.kotlin.analysis.api.components.KaReferenceResolveProvider
import org.jetbrains.kotlin.analysis.api.descriptors.Fe10AnalysisFacade
import org.jetbrains.kotlin.analysis.api.descriptors.KaFe10Session
import org.jetbrains.kotlin.analysis.api.descriptors.components.base.KaFe10SessionComponent
import org.jetbrains.kotlin.analysis.api.descriptors.symbols.descriptorBased.base.toKtSymbol
import org.jetbrains.kotlin.analysis.api.symbols.KaSymbol
import org.jetbrains.kotlin.idea.references.KtReference
import org.jetbrains.kotlin.idea.references.KtSimpleNameReference
import org.jetbrains.kotlin.references.fe10.base.KtFe10Reference
import org.jetbrains.kotlin.resolve.BindingContext

internal class KaFe10ReferenceResolveProvider(
    override val analysisSession: KaFe10Session,
) : KaReferenceResolveProvider(), KaFe10SessionComponent {
    override fun resolveToSymbols(reference: KtReference): Collection<KaSymbol> {
        require(reference is KtFe10Reference)
        val bindingContext = analysisContext.analyze(reference.element, Fe10AnalysisFacade.AnalysisMode.PARTIAL)
        return reference.getTargetDescriptors(bindingContext).mapNotNull { descriptor ->
            descriptor.toKtSymbol(analysisContext)
        }
    }

    override fun isImplicitReferenceToCompanion(reference: KtReference): Boolean {
        if (reference !is KtSimpleNameReference) {
            return false
        }
        val bindingContext = analysisContext.analyze(reference.element, Fe10AnalysisFacade.AnalysisMode.PARTIAL)
        return bindingContext[BindingContext.SHORT_REFERENCE_TO_COMPANION_OBJECT, reference.element] != null
    }
}